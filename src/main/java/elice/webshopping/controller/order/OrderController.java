package elice.webshopping.controller.order;

import elice.webshopping.domain.order.OrderRequestDto;
import elice.webshopping.domain.order.OrderResponseDto;
import elice.webshopping.domain.order.OrderStatus;
import elice.webshopping.domain.order.OrderStatusUpdateRequestDto;
import elice.webshopping.domain.user.CustomUserDetails;
import elice.webshopping.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody OrderRequestDto orderRequestDto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(orderService.createOrder(orderRequestDto, user.getUser()));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderResponseDto>> getUserOrders(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(orderService.getOrdersByUser(user.getUser()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderResponseDtoById(orderId));
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderStatus(orderId));
    }

    @PatchMapping("/status")
    public ResponseEntity<OrderStatus> updateOrderStatus(
            @RequestBody OrderStatusUpdateRequestDto orderStatusUpdateRequestDto
    ) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderStatusUpdateRequestDto));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrderForAdmin(@PathVariable Long orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.ok().build();
    }
}
