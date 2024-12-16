package elice.webshopping.service.order;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.order.OrderRequestDto;
import elice.webshopping.domain.order.OrderResponseDto;
import elice.webshopping.domain.productOrder.ProductOrder;
import elice.webshopping.domain.user.User;
import elice.webshopping.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

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
    public OrderResponseDto getOrderById(Long orderId) {
        Order findOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        if (findOrder.getDeletedAt() == null) {
            return OrderResponseDto.from(findOrder);
        } else {
            throw new NotFoundException("Order not found");
        }
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

//    public List<OrderResponseDto> to()
}
