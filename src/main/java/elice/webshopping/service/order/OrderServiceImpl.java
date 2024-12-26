package elice.webshopping.service.order;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.order.OrderRequestDto;
import elice.webshopping.domain.order.OrderResponseDto;
import elice.webshopping.domain.order.Receiver;
import elice.webshopping.domain.productOrder.ProductOrder;
import elice.webshopping.domain.productOrder.ProductOrderRequestDto;
import elice.webshopping.domain.user.User;
import elice.webshopping.exception.common.NoContentsException;
import elice.webshopping.exception.order.admin.OrderNotCanceledException;
import elice.webshopping.exception.order.admin.OrderNotFoundException;
import elice.webshopping.exception.order.user.OrderReadyForShippingException;
import elice.webshopping.repository.order.OrderRepository;
import elice.webshopping.repository.product.ProductRepository;
import elice.webshopping.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ReceiverService receiverService;
    private final ProductRepository productRepository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, User user) {

        Receiver receiver = receiverService.createReceiver(orderRequestDto.receiverRequestDto());

        List<ProductOrder> productOrders = new ArrayList<>();
        for (ProductOrderRequestDto productOrderRequestDto : orderRequestDto.productOrdersRequestDto()) {
            ProductOrder productOrder = ProductOrder.of(
                    productOrderRequestDto,
                    productRepository.findById(productOrderRequestDto.productId()).orElseThrow(
                            () -> new NoContentsException("No product found with id: " + productOrderRequestDto.productId())
                    )
                    // ProductService 에 서버 내부에서만 호출할 조회 메서드 필요함 -> 팀원에게 리팩토링 요청 필요
            );

            productOrders.add(productOrder);
        }

        Order order = orderRepository.save(Order.of(orderRequestDto, user, receiver, productOrders));
        // 선 주문 생성 => 후 결제 처리
        // 결제 실패시 주문 상태 -> PENDING
        // 결제 성공시 주문 상태 -> ORDERED
        return OrderResponseDto.from(order);
    }

    @Override
    public List<OrderResponseDto> getOrders() {
        return orderRepository.findAll()
                .stream()
                .filter(order -> order.getDeletedAt() == null)
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
