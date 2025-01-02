package elice.webshopping.domain.order;

import elice.webshopping.domain.payment.Payment;
import elice.webshopping.domain.productOrder.ProductOrder;
import elice.webshopping.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private String summaryTitle;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private String request;

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

    @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductOrder> productOrders = new ArrayList<>();

    @Setter
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;

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
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static Order of(
            OrderRequestDto orderRequestDto,
            User user,
            Receiver receiver
    ) {
        return Order.builder()
                .orderNumber(generateOrderNumber())
                .status(OrderStatus.ORDERED)
                .summaryTitle(orderRequestDto.summaryTitle())
                .totalPrice(orderRequestDto.totalPrice())
                .request(orderRequestDto.request())
                .user(user)
                .receiver(receiver)
                .build();
    }

    private static String generateOrderNumber() {
        return (LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + UUID.randomUUID())
                .substring(0, 20);
    }

    public void cancelOrder() {
        this.deletedAt = LocalDateTime.now();
        this.status = OrderStatus.CANCELED;
    }
}
