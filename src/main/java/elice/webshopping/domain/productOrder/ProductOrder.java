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
    private Long productOrderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private int quantity;

    public static ProductOrder of(
            ProductOrderRequestDto productOrderRequestDto, Product product, Order order
    ) {
        return new ProductOrder(
                null,
                product,
                order,
                productOrderRequestDto.totalPrice(),
                productOrderRequestDto.quantity()
        );
    }
}
