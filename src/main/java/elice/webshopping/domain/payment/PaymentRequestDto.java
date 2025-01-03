package elice.webshopping.domain.payment;

public record PaymentRequestDto(
        Long orderId,
        int amount,
        String paymentKey
) {
}
