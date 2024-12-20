package elice.webshopping.domain.productOrder;

public record ProductOrderRequestDto(
        Long productId,
        // price 는 클라이언트에서 가격 조작할 가능성이 있지 않을까?
        // 할인 등을 적용한다면 가격을 받는 게 좋아보이긴 함
        int price,
        int quantity
) {}
