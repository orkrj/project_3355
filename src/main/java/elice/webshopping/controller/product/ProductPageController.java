package elice.webshopping.controller.product;

import elice.webshopping.domain.product.ProductResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductPageController {
    // 2. 상품 단건 조회 (GET) - /product/{productId}
    @GetMapping("/product/{productId}")
    public String getProductById() throws Exception{
        return "redirect:/product-detail/product-detail.html";
    }

}
