package elice.webshopping.domain.order;

import elice.webshopping.domain.payment.Payment;
import elice.webshopping.domain.payment.PaymentResponseDto;
import elice.webshopping.domain.productOrder.ProductOrderResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto (
        String orderNumber,
        OrderStatus status,
        String summaryTittle,
        int totalPrice,
        String request,
        ReceiverResponseDto receiver,
        List<ProductOrderResponseDto> productOrdersResponseDto
) {

    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(
                order.getOrderNumber(),
                order.getStatus(),
                order.getSummaryTitle(),
                order.getTotalPrice(),
                order.getRequest(),
                ReceiverResponseDto.from(order),
                ProductOrderResponseDto.from(order)
        );
    }
}
