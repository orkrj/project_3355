package elice.webshopping.domain.productOrder;

public record ProductOrderRequestDto(
        Long productId,
        Long orderId,
        int totalPrice,
        int quantity
) {}
