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

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    public static ProductOrder of(ProductOrderRequestDto productOrderRequestDto, Product product) {
        return new ProductOrder(
                null,
                product,
                productOrderRequestDto.price(),
                productOrderRequestDto.quantity()
        );
    }
}
