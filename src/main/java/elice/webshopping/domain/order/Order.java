package elice.webshopping.domain.order;

import elice.webshopping.domain.common.BaseEntity;
import elice.webshopping.domain.productOrder.ProductOrder;
import elice.webshopping.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrder> productOrders = new ArrayList<>();

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
            User user,
            Receiver receiver,
            List<ProductOrder> productOrders
    ) {
        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .payment(orderRequestDto.payment())
                .message(orderRequestDto.message())
                .status(OrderStatus.ORDER_COMPLETED)
                .totalPrice(orderRequestDto.totalPrice())
                .user(user)
                .receiver(receiver)
                .build();

        for (ProductOrder productOrder : productOrders) {
            order.getProductOrders().add(productOrder);
            productOrder.setOrder(order);
        }

        return order;
    }

    private static String generateOrderNumber() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + (int) (Math.random() * 1000);
    }
}
