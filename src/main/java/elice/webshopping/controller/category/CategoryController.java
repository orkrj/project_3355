package elice.webshopping.controller.category;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/category")
public class CategoryController {

    // /category/코트/개수
    //카테고리 조회
    @GetMapping("{categoryName}")
    public void findAllProductBy(String categoryName){

    }

    //카테고리 생성

    //카테고리 수정

    //카테고리 삭제
}


