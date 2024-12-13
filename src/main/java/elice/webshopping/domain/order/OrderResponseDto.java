package elice.webshopping.domain.order;

import elice.webshopping.domain.productOrder.ProductOrderResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto (
        String orderNumber,
        String payment,
        String message,
        OrderStatus status,
        int totalPrice,
        LocalDateTime createdAt,
        ReceiverResponseDto receiver,
        List<ProductOrderResponseDto> productOrdersResponseDto
) {}
