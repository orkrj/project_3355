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
    private List<MultipartFile> mainImageFiles; // 메인 이미지 파일
    private List<MultipartFile> descriptionImageFiles; // 상세 이미지 파일

    public ProductRequestDto(String name, int price, String description, int stockQuantity, Long categoryId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
    }
}
