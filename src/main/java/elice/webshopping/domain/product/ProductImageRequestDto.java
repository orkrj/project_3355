package elice.webshopping.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageRequestDto {
    private List<String> mainImageUrls; // 메인 이미지 URL 목록
    private List<String> descriptionImageUrls; // 상세 이미지 URL 목록
    private String imageUrl; // 이미지 URL (수정 시 사용)
    private ProductImage.ImageType imageType; // 이미지 타입 (수정 시 사용)
}
