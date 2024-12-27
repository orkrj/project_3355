package elice.webshopping.domain.productOrder;

public record ProductOrderRequestDto(
        Long productId,
        Long orderId,
        int price,
        int quantity
) {}
