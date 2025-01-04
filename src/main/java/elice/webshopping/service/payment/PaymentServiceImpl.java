package elice.webshopping.service.payment;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.payment.Payment;
import elice.webshopping.domain.payment.PaymentRequestDto;
import elice.webshopping.domain.payment.PaymentResponseDto;
import elice.webshopping.repository.payment.PaymentRepository;
import elice.webshopping.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) {
        Order order = orderService.getOrderEntityById(paymentRequestDto.orderId());
        Payment payment = paymentRepository.save(Payment.from(paymentRequestDto, order));

        log.info("order : {}", order.getStatus());
        log.info("Payment created: {}", payment.getAmount());

        order.setPayment(payment);

        log.info("Payment created: {}", order.getPayment().getAmount());
        return PaymentResponseDto.from(payment);
    }
}
