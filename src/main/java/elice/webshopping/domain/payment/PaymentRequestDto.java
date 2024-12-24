package elice.webshopping.domain.payment;

public record PaymentRequestDto(
        int amount,
        String orderNumber,
        String paymentKey
) {
}
