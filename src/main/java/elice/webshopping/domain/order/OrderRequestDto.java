package elice.webshopping.domain.order;

import elice.webshopping.domain.productOrder.ProductOrderRequestDto;

import java.util.List;

public record OrderRequestDto(
//        summaryTitle,
//        totalPrice,
//        receiver,
//        request,

        String summaryTitle,
        int totalPrice,
        ReceiverRequestDto receiver,
        String request
        // productOrder 는 굳이 post 안 받는듯?
) {}
