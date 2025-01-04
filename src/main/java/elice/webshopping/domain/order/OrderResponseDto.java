package elice.webshopping.domain.order;

import elice.webshopping.domain.payment.Payment;
import elice.webshopping.domain.payment.PaymentResponseDto;
import elice.webshopping.domain.productOrder.ProductOrderResponseDto;
import elice.webshopping.domain.user.UserResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto (
        Long orderId,
        String orderNumber,
        OrderStatus status,
        String summaryTitle,
        int totalPrice,
        String request,
        LocalDateTime createdAt,
        UserResponseDto user,
        ReceiverResponseDto receiver,
        List<ProductOrderResponseDto> productOrdersResponseDto
) {

    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(
                order.getOrderId(),
                order.getOrderNumber(),
                order.getStatus(),
                order.getSummaryTitle(),
                order.getTotalPrice(),
                order.getRequest(),
                order.getCreatedAt(),
                new UserResponseDto(order.getUser()),
                ReceiverResponseDto.from(order),
                ProductOrderResponseDto.from(order)
        );
    }
}
