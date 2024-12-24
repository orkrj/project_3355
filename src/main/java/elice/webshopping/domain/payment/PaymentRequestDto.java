package elice.webshopping.domain.payment;

public record PaymentRequestDto(
        int amount, // suppliedAmount
        String orderNumber, // orderId
        String paymentKey // paymentKey
) {
}
