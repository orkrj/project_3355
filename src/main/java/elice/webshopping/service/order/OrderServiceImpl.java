package elice.webshopping.service.order;

import elice.webshopping.domain.order.*;
import elice.webshopping.domain.user.User;
import elice.webshopping.exception.common.NoContentsException;
import elice.webshopping.exception.order.admin.OrderNotCanceledException;
import elice.webshopping.exception.order.admin.OrderNotFoundException;
import elice.webshopping.exception.order.user.InvalidOrderStateException;
import elice.webshopping.exception.order.user.OrderReadyForShippingException;
import elice.webshopping.repository.order.OrderRepository;
import elice.webshopping.service.payment.PaymentService;
import elice.webshopping.service.productOrder.ProductOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ReceiverService receiverService;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, User user) {
        Receiver receiver = receiverService.createReceiverEntity(orderRequestDto.receiver());
        Order order = orderRepository.saveAndFlush(Order.of(orderRequestDto, user, receiver));

        return OrderResponseDto.from(order);
    }

    @Override
    public List<OrderResponseDto> getOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponseDto::from)
                .toList();
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(User user) {
        return orderRepository.findAll()
                .stream()
                .filter(order -> Objects.equals(order.getUser().getUserId(), user.getUserId()))
                .map(OrderResponseDto::from)
                .toList();
    }

    @Override
    public OrderResponseDto getOrderResponseDtoById(Long orderId) {
        return OrderResponseDto.from(getOrderEntityById(orderId));
    }

    @Override
    public Order getOrderEntityById(Long orderId) {
        return orderRepository.findById(orderId).filter(order -> order.getDeletedAt() == null)
                .orElseThrow(() -> new NoContentsException("Order " + orderId + " not found"));
    }

    @Override
    public OrderStatus getOrderStatus(Long orderId) {
        return getOrderEntityByIdIncludeDeletedAtIsNotNull(orderId).getStatus();
    }

    @Override
    @Transactional
    public OrderStatus updateOrderStatus(OrderStatusUpdateRequestDto orderStatusUpdateRequestDto) {
        Order order = getOrderEntityByIdIncludeDeletedAtIsNotNull(orderStatusUpdateRequestDto.orderId());
        order.setStatus(orderStatusUpdateRequestDto.orderStatus());

        return order.getStatus();
    }


    @Override
    public Order getOrderEntityByIdIncludeDeletedAtIsNotNull(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public Order getOrderEntityByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }


    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = getOrderEntityById(orderId);
        if (order.getStatus().canBeCanceled()) {
            order.cancelOrder();
        } else {
            throw new OrderReadyForShippingException(order.getStatus(), order.getOrderNumber());
        }
    }

    @Override
    @Transactional
    public void deleteOrderById(Long orderId) {
        /** TODO
         * 역할 필요: ADMIN
         * 직권 취소도 고려해야 함
         */

        Order order = getOrderEntityByIdIncludeDeletedAtIsNotNull(orderId);
        if (order.getDeletedAt() != null) {
            orderRepository.deleteById(orderId);
        } else {
            throw new OrderNotCanceledException(orderId);
        }
    }

    @Override
    @Transactional
    public void deleteOrders(User user) {
        List<Order> orders = orderRepository.findAll()
                .stream()
                .filter(order -> order.getUser().equals(user))
                .toList();

        orders.forEach(this::isInValidStatusToCancel);
        orders.forEach(order -> orderRepository.deleteById(order.getOrderId()));
    }

    private void isInValidStatusToCancel(Order order) {
        Set<OrderStatus> invalidStatusToDelete = Set.of(
                OrderStatus.PENDING, OrderStatus.ORDERED, OrderStatus.SHIPPING
        );

        if (invalidStatusToDelete.contains(order.getStatus())) {
            throw new InvalidOrderStateException(
                    "진행 중인 주문이 있으면 회원 탈퇴할 수 없습니다.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
