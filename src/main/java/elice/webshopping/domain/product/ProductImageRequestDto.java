package elice.webshopping.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageRequestDto {
    private List<MultipartFile> mainImageFiles;        // 메인 이미지 파일 (등록 시 사용)
    private List<MultipartFile> descriptionImageFiles; // 상세 이미지 파일 (등록 시 사용)
    private String imageUrl;                           // 이미지 URL (수정 시 사용)
    private ProductImage.ImageType imageType;          // 이미지 타입 (MAIN, DESCRIPTION)

    public ProductImageRequestDto(List<MultipartFile> mainImageFiles, List<MultipartFile> descriptionImageFiles) {
        this.mainImageFiles = mainImageFiles;
        this.descriptionImageFiles = descriptionImageFiles;
    }
}
