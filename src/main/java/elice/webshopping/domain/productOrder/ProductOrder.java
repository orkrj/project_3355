package elice.webshopping.domain.productOrder;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products_orders")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;
}
