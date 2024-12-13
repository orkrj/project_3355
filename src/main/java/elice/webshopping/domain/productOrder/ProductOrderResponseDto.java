package elice.webshopping.domain.productOrder;

public record ProductOrderResponseDto (
        Long productId,
        int quantity
) {}
