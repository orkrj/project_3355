package elice.webshopping.domain.payment;

import elice.webshopping.domain.common.BaseEntity;
import elice.webshopping.domain.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "payments")
@EntityListeners(AuditingEntityListener.class)
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private int amount;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String paymentKey;

    public static Payment from(PaymentRequestDto paymentRequestDto, Order order) {
        return Payment.builder()
                .amount(paymentRequestDto.amount())
                .order(order)
                .paymentKey(paymentRequestDto.paymentKey())
                .build();
    }
}
