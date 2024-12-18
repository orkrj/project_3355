package elice.webshopping.domain.product;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products_images")
@Builder
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    // setProduct() 메서드 추가 (양방향 관계 설정)
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // setImageUrl 및 setImageType 메서드 추가
    @Setter
    @Column(nullable = false, length = 255)
    private String imageUrl;

    @Setter
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    // 변수로 저장해서 변수에서 뽑아서 저장하도록
    public enum ImageType {
        MAIN, DESCRIPTION
    }

    // Builder 메서드에서 product를 설정할 수 있도록 @Builder에 추가
    public static ProductImage createWithProduct(Product product, String imageUrl, ImageType imageType) {
        return ProductImage.builder()
                .imageUrl(imageUrl)
                .imageType(imageType)
                .product(product)  // product 설정
                .build();
    }

}
