package elice.webshopping.domain.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CategoryRequestDto {

    private Long id;

    private Long parentId;

    @NotBlank
    @Size(max=20)
    private String name;


}
