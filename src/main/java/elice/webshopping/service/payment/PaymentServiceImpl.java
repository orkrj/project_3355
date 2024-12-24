package elice.webshopping.service.payment;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.payment.Payment;
import elice.webshopping.domain.payment.PaymentRequestDto;
import elice.webshopping.domain.payment.PaymentResponseDto;
import elice.webshopping.repository.payment.PaymentRepository;
import elice.webshopping.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto paymentRequestDto) {
        Order order = orderService.getOrderEntityByOrderNumber(paymentRequestDto.orderNumber());
        Payment payment = paymentRepository.save(Payment.from(paymentRequestDto, order));

        order.setPayment(payment);
        return PaymentResponseDto.from(payment);
    }
}
