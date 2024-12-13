package elice.webshopping.domain.order;

public record OrderRequestDto(
        Long userId,
        // orderNumber 자동 생성 -> 날짜? uuid?
        String payment,
        String message,
        // status -> 기본 값 -> 주문 완료
        int totalPrice,
        // createdAt, updatedAt -> 지금 시간
        Long receiverId
) {}
