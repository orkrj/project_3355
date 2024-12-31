package elice.webshopping.domain.product;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    private String name;
    private int price;
    private String description;
    private int stockQuantity;
    private Long categoryId;
}
