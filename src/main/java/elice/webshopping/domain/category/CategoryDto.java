package elice.webshopping.domain.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryDto {

    private Long id;

    private Long parentId;

    @NotBlank
    @Size(max = 20, message = "이름은 1자 이상 20자 이하로 입력해주세요.")
    private String name;

}
