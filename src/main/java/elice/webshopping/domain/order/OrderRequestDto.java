package elice.webshopping.domain.order;

import elice.webshopping.domain.productOrder.ProductOrderRequestDto;

import java.util.List;

public record OrderRequestDto(
        Long userId,
        // orderNumber 자동 생성
        String payment,
        // status -> 기본 값 -> 주문 완료
        int totalPrice,
        // createdAt, updatedAt -> 지금 시간
        Long receiverId,
        List<ProductOrderRequestDto> productOrdersRequestDto
) {}
