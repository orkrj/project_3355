package elice.webshopping.domain.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CategoryRequestDto {

    @NotBlank
    @Size(max=20)
    private String name;

    private Long parentId;
}
