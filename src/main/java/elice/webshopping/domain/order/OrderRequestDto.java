package elice.webshopping.domain.order;

import elice.webshopping.domain.productOrder.ProductOrderRequestDto;

import java.util.List;

public record OrderRequestDto(
        String summaryTitle,
        int totalPrice,
        ReceiverRequestDto receiver,
        String request
) {}
