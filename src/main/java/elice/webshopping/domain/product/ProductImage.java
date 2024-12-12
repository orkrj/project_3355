package elice.webshopping.domain.product;

import elice.webshopping.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products_images")
@EntityListeners(AuditingEntityListener.class)
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, length = 255)
    private String imageUrl;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @Embedded
    private BaseEntity baseEntity;

    public enum ImageType {
        MAIN, DESCRIPTION
    }
}
