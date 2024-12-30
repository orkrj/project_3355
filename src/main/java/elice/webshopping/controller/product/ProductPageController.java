package elice.webshopping.controller.product;

import elice.webshopping.domain.product.ProductResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class ProductPageController {
    // 1-1. 상품 전체 조회 (GET) - /api/product/page
    @GetMapping("/products")
    public String getPagedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {
        return "redirect:/product-list/product-list.html?page=" + page + "&size=" + size + "&sortBy=" + sortBy + "&direction=" + direction;
    }

    // 2. 상품 단건 조회 (GET) - /product/{productId}
    @GetMapping("/product/{productId}")
    public String getProductById(@PathVariable Long productId) throws Exception{
        return "redirect:/product-detail/product-detail.html";
    }

}
