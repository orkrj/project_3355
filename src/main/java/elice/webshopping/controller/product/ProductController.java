package elice.webshopping.controller.product;

import elice.webshopping.domain.product.ProductRequestDto;
import elice.webshopping.domain.product.ProductResponseDto;
import elice.webshopping.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. 상품 전체 조회 (GET) - /products
    @GetMapping
    public String getAllProducts(Model model) {
        List<ProductResponseDto> products = productService.getAllProducts();
        model.addAttribute("products", products); // 모델에 상품 목록을 추가
        return "product/list"; // 상품 목록을 표시할 페이지 (product/list.html)
    }

    // 2. 상품 단건 조회 (GET) - /products/{id}
    @GetMapping("/{productId}")
    public String getProductById(@PathVariable Long productId, Model model) {
        ProductResponseDto product = productService.getProductById(productId);
        model.addAttribute("product", product); // 모델에 단일 상품 정보를 추가
        return "product/detail"; // 상품 상세 페이지 (product/detail.html)
    }

    // 3. 상품 생성 페이지 이동 (GET) - /products/form
    @GetMapping("/form")
    public String productForm(Model model) {
        // 상품 생성 페이지로 이동 (모델에 필요한 데이터를 담을 수 있음)
        // 예를 들어 카테고리 리스트나 다른 모델 데이터를 넘겨줄 수 있음
        return "product/form"; // Thymeleaf 등으로 처리할 HTML 파일 반환
    }

    // 4. 상품 생성 (POST) - /products
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestBody ProductRequestDto request) {
        productService.createProduct(request);
        return "redirect:/products"; // 상품 목록으로 리다이렉트
    }

    // 5. 상품 수정 페이지 이동 (GET) - /products/{id}/form
    @GetMapping("/{productId}/form")
    public String productEditForm(@PathVariable Long productId, Model model) {
        ProductResponseDto product = productService.getProductById(productId);
        model.addAttribute("product", product); // 모델에 상품 정보 추가
        return "product/editForm"; // 상품 수정 페이지 (product/editForm.html)
    }

    // 6. 상품 수정 (PUT) - /products/{id}
    @PutMapping("/{productId}")
    public String updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto request) {
        productService.updateProduct(productId, request);
        return "redirect:/products/" + productId; // 수정된 상품의 상세 페이지로 리다이렉트
    }

    // 7. 상품 삭제 (Soft Delete) (DELETE) - /products/{id}
    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/products"; // 상품 목록 페이지로 리다이렉트
    }

}