package elice.webshopping.domain.order;

import elice.webshopping.domain.productOrder.ProductOrderRequestDto;

import java.util.List;

public record OrderRequestDto(
        // orderNumber 자동 생성
        // status -> 기본 값 -> 주문 완료
        int totalPrice,
        // createdAt, updatedAt -> 지금 시간
        ReceiverRequestDto receiverRequestDto,
        List<ProductOrderRequestDto> productOrdersRequestDto
) {}
