package elice.webshopping.controller.category;

import elice.webshopping.domain.category.CategoryDto;
import elice.webshopping.domain.product.Product;
import elice.webshopping.domain.product.ProductResponseDto;
import elice.webshopping.service.category.CategoryService;
import elice.webshopping.service.product.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    //사용자 :user
    //카테고리 조회
    @GetMapping("{categoryName}")
    public ResponseEntity<List<ProductResponseDto>> findProductBy(@PathVariable("categoryName") String categoryName){

        log.info("Category name: {}", categoryName);
        List<ProductResponseDto> product = productService.findProductBy(categoryName);

        //null 조회시 null을 반환하는게 아닌 빈리스트 배열을 반환하도록
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    //사용자 :admin

    //카테고리 전부 출력 => 여기서 생성/수정/삭제 처리
    @GetMapping ("findAll")
    public ResponseEntity<List<CategoryDto>> findAll(){
        List<CategoryDto> CategoryDtos = categoryService.findAll();

        //categorieResponseDto 생성해야함 => CategoryDto으로 요청 및 응답 동시 처리
        return new ResponseEntity<>(CategoryDtos, HttpStatus.OK);
    }


    //카테고리 생성
    @PostMapping("create")
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto){
        //CategoryDto로 받아야하나 ID는 무조건 null인데. RequestParam으로 해야하나?

        CategoryDto categoryDtoResponse = categoryService.save(categoryDto.getName(), categoryDto.getParentId());
        return new ResponseEntity<>(categoryDtoResponse, HttpStatus.OK);
    }


    //카테고리 수정
    @PutMapping("update")
    public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto categoryDto){

        log.info("이름:{}, id:{}",categoryDto.getName(), categoryDto.getId());
        CategoryDto categoryDtoResponse = categoryService.update(categoryDto.getName(), categoryDto.getId());
        return new ResponseEntity<>(categoryDtoResponse, HttpStatus.OK);
    }

    //카테고리 삭제
    @DeleteMapping("delete/{categoryId}")
    @ResponseStatus(HttpStatus.OK) //본문이 필요하지 않으니 ResponseStatus 사용
    public void delete(@PathVariable("categoryId") Long categoryId){

        log.info("categoryId:{}",categoryId);
        categoryService.delete(categoryId);
    }
}


