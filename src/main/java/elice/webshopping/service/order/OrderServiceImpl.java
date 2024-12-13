package elice.webshopping.service.order;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.order.OrderRequestDto;
import elice.webshopping.domain.order.OrderResponseDto;
import elice.webshopping.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        return null;
    }

    @Override
    public List<OrderResponseDto> getOrders() {
        return List.of();
    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        return null;
    }

    @Override
    public Order getOrderEntityById(Long orderId) {
        return null;
    }

    @Override
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto orderRequestDto) {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) {

    }
}
