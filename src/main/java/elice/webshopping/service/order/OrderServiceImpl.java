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
        Order findOrder = getOrderEntityById(orderId);
        return OrderResponseDto.from(findOrder);
    }

    @Override
    public Order getOrderEntityById(Long orderId) {
        return orderRepository.findById(orderId).filter(order -> order.getDeletedAt() == null)
                .orElseThrow(() -> new NotFoundException("Order " + orderId + " not found"));
    }

    @Override
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto orderRequestDto) {
        return null;
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = getOrderEntityById(orderId);
        if (order.getStatus().orderCanBeDeleted()) {
            order.deleteOrder();
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

        Order order = getOrderEntityById(orderId);
        if (order.getDeletedAt() != null) {
            orderRepository.deleteById(orderId);
        } else {
            throw new OrderNotCanceledException(orderId);
        }
    }
}
