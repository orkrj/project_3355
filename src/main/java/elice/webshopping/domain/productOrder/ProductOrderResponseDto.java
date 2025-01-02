package elice.webshopping.domain.productOrder;

import elice.webshopping.domain.order.Order;

import java.util.List;

// TODO 화면에 Product 내 어떤 필드가 필요한지 확인해야 함
public record ProductOrderResponseDto (
        Long productId,
        Long orderId,
        int totalPrice,
        int quantity
) {

    public static ProductOrderResponseDto from(ProductOrder productOrder) {
        return new ProductOrderResponseDto(
                productOrder.getProduct().getProductId(),
                productOrder.getOrder().getOrderId(),
                productOrder.getTotalPrice(),
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
