package elice.webshopping.domain.order;

public record OrderStatusUpdateRequestDto(
        Long orderId,
        OrderStatus orderStatus
) {
}
