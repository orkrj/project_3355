package elice.webshopping.domain.order;

import elice.webshopping.domain.productOrder.ProductOrderRequestDto;

import java.util.List;

public record OrderRequestDto(
        // orderNumber 자동 생성
        String payment, // -> 결제 API 응답이랑 연동해야 함
        // status -> 기본 값 -> 주문 완료
        int totalPrice,
        // createdAt, updatedAt -> 지금 시간
        ReceiverRequestDto receiverRequestDto,
        List<ProductOrderRequestDto> productOrdersRequestDto
) {}
