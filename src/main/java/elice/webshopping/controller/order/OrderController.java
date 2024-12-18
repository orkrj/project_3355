package elice.webshopping.controller.order;

import elice.webshopping.domain.order.OrderRequestDto;
import elice.webshopping.domain.order.OrderResponseDto;
import elice.webshopping.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // TODO 생성 로직 미구현: UserService 필요
    public ResponseEntity<OrderResponseDto> createOrder(OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok(orderService.createOrder(orderRequestDto));
    }

    public ResponseEntity<List<OrderResponseDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    public ResponseEntity<OrderResponseDto> getOrderById(Long orderId) {
        return ResponseEntity.ok(orderService.getOrderResponseDtoById(orderId));
    }

    public ResponseEntity<Void> cancelOrder(Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteOrderForAdmin(Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
