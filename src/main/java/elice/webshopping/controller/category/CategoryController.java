package elice.webshopping.controller.category;

import elice.webshopping.domain.product.Product;
import elice.webshopping.service.category.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // /category/코트/개수
    //카테고리 조회
    @GetMapping("{categoryName}")
    public void findProductBy(String categoryName){

        List<Product> product = categoryService.findProductBy(categoryName);

    }

    //카테고리 생성
    @PostMapping("create")
    public void createCategory(){

    }

    //카테고리 수정
    @PutMapping("update/{id}")
    public void updateCategory(){

    }

    //카테고리 삭제
    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable long categoryId){
        categoryService.delete(categoryId);
    }
}


