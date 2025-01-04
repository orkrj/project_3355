package elice.webshopping.service.order;

import elice.webshopping.domain.order.*;
import elice.webshopping.domain.user.User;
import elice.webshopping.exception.common.NoContentsException;
import elice.webshopping.exception.order.admin.OrderNotCanceledException;
import elice.webshopping.exception.order.admin.OrderNotFoundException;
import elice.webshopping.exception.order.user.OrderReadyForShippingException;
import elice.webshopping.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ReceiverService receiverService;
    // private final ProductOrderService productOrderService;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, User user) {

        Receiver receiver = receiverService.createReceiverEntity(orderRequestDto.receiver());
        Order order = orderRepository.saveAndFlush(Order.of(orderRequestDto, user, receiver));

//        List<ProductOrder> productOrders = productOrderService.getProductOrdersByOrderId(order);
//        order.setProductOrders(productOrders);

        // 1. order 가 먼저 만들어짐 2. product-order 가 만들어짐
        // TODO productOrder.getProductOrdersByOrderId() 호출로 필드 할당해줘야 함
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
    public Order getOrderEntityByIdIncludeDeletedAtIsNotNull(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public Order getOrderEntityByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }

    /**
     * 주문 수정 - 사용자는 주문 완료 후 배송이 시작되기 전까지 주문 정보를 수정할 수 있다.
     * -> 결제 기능이 들어가면 주문 수정이 불가능함 -> 환불(주문 취소) => 재구매 시스템임
     * 따라서, 배송 전이라면 배송 정보만 수정하는 게 좋아보임
     */

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
    public void deleteOrder(Long orderId) {
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
}
