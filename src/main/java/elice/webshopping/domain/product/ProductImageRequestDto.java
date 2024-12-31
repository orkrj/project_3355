package elice.webshopping.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageRequestDto {
    private List<MultipartFile> mainImageFiles;
    private List<MultipartFile> descriptionImageFiles;
}
