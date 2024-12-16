package elice.webshopping.domain.category;

import lombok.Getter;

@Getter
public class CategoryRequestDto {

    private String name;

    private Long parentId;
}
