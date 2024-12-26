package elice.webshopping.domain.payment;

public record PaymentResponseDto(
        int amount
) {

    public static PaymentResponseDto from(Payment payment) {
        return new PaymentResponseDto(payment.getAmount());
    }
}
