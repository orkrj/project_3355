package elice.webshopping.domain.order;

import elice.webshopping.domain.common.BaseEntity;
import elice.webshopping.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// @EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(nullable = false)
    private String payment;

    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private int totalPrice;

    /**
     * TODO
     * audit 가 안 됨 -> superclass 로 한 번 시도해볼 것
     */
//    @Embedded
//    private BaseEntity baseEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Receiver receiver;

    // @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    // @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static Order of(
            OrderRequestDto orderRequestDto,
            ReceiverRequestDto receiverRequestDto,
            User user,
            Receiver receiver) {

        return Order.builder()
                .orderNumber(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                // 초 단위로 같은 주문은 보장 안 한다는 가정
                .payment(orderRequestDto.payment())
                .message(orderRequestDto.message())
                .status(OrderStatus.ORDER_COMPLETED)
                .totalPrice(orderRequestDto.totalPrice())
                .payment(orderRequestDto.payment())
                .user(user)
                .receiver(receiver)
                .build();
    }
}
