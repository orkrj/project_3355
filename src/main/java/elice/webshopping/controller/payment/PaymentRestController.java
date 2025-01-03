package elice.webshopping.controller.payment;

import elice.webshopping.domain.payment.PaymentRequestDto;
import elice.webshopping.domain.payment.PaymentResponseDto;
import elice.webshopping.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentRestController {

    private final PaymentService paymentService;

    @PostMapping("/api/payment")
    public ResponseEntity<PaymentResponseDto> savePayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        return ResponseEntity.ok(paymentService.createPayment(paymentRequestDto));
    }
}
