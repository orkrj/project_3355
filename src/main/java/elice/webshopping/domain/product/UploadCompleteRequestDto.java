package elice.webshopping.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UploadCompleteRequestDto {
    private Long productId;
    private String key;
    private ProductImage.ImageType imageType;
}
