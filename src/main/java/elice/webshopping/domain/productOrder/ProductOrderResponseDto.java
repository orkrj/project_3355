package elice.webshopping.domain.productOrder;

import elice.webshopping.domain.order.Order;

import java.util.List;


public record ProductOrderResponseDto (
        Long productId,
        int quantity
) {

    public static ProductOrderResponseDto from(ProductOrder productOrder) {
        return new ProductOrderResponseDto(
                productOrder.getProduct().getProductId(),
                productOrder.getQuantity()
        );
    }

    public static List<ProductOrderResponseDto> from(Order order) {
        return order.getProductOrders()
                .stream()
                .map(ProductOrderResponseDto::from)
                .toList();
    }
}
