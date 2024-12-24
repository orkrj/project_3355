package elice.webshopping.service.payment;

import elice.webshopping.domain.payment.PaymentRequestDto;
import elice.webshopping.domain.payment.PaymentResponseDto;

public interface PaymentService {

    //== 결제 정보 저장 ==//
    PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto);
}
