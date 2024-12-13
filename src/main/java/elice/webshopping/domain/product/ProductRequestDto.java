package elice.webshopping.domain.product;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    private String name;
    private int price;
    private String description;
    private int stockQuantity;
    private Long categoryId;
    private List<String> mainImageUrls; // 메인 이미지 URL 목록
    private List<String> descriptionImageUrls; // 상세 이미지 URL 목록
}
