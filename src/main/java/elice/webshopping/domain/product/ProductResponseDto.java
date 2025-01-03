package elice.webshopping.domain.product;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Long productId;                        // 제품 ID
    private String name;                           // 제품명
    private int price;                             // 가격
    private String description;                    // 설명
    private int stockQuantity;                     // 재고 수량
    private Long categoryId;                   // 카테고리 이름
    private String categoryName;                   // 카테고리 이름
    private LocalDateTime createdAt;               // 생성 일자
    private LocalDateTime updatedAt;               // 수정 일자
    private List<String> mainImageUrls;            // 메인 이미지 URL 목록
    private List<String> descriptionImageUrls;     // 상세 이미지 URL 목록
}
