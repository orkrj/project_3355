package elice.webshopping.domain.payment;

public record PaymentRequestDto(
        String orderNumber,
        int amount,
        String paymentKey
) {
}
