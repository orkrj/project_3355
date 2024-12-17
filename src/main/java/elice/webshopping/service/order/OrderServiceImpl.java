package elice.webshopping.service.order;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.order.OrderRequestDto;
import elice.webshopping.domain.order.OrderResponseDto;
import elice.webshopping.exception.order.admin.OrderNotCanceledException;
import elice.webshopping.exception.order.user.OrderReadyForShippingException;
import elice.webshopping.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        // userService 필요
        return null;
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
                .orElseThrow(() -> new NotFoundException("Order " + orderId + " not found"));
    }

    @Override
    public Order getOrderEntityByIdIncludeDeletedAtIsNotNull(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order " + orderId + " not found"));
    }

    @Override
    /**
     * 주문 수정 - 사용자는 주문 완료 후 배송이 시작되기 전까지 주문 정보를 수정할 수 있다.
     * -> 결제 기능이 들어가면 주문 수정이 불가능함 -> 환불(주문 취소) => 재구매 시스템임
     * 따라서, 배송 전이라면 배송 정보만 수정하는 게 좋아보임
     */
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto orderRequestDto) {
        return null;
    }

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
