package elice.webshopping.controller.product;

import elice.webshopping.domain.product.ProductRequestDto;
import elice.webshopping.domain.product.ProductResponseDto;
import elice.webshopping.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products); // HTTP 200 OK와 함께 상품 목록 반환
    }

    // 2. 상품 단건 조회 (GET) - /products/{id}
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        ProductResponseDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product); // HTTP 200 OK와 함께 단일 상품 반환
    }

    // 3. 상품 생성 페이지 이동 (GET) - /products/form
    @GetMapping("/form")
    public ResponseEntity<String> productForm() {
        return ResponseEntity.ok("product/form"); // HTML 파일 이름을 응답으로 반환
    }

    // 4. 상품 생성 (POST) - /products
    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody @Valid ProductRequestDto request) {
        productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // HTTP 201 CREATED
    }

    // 5. 상품 수정 페이지 이동 (GET) - /products/{id}/form
    @GetMapping("/{productId}/form")
    public ResponseEntity<ProductResponseDto> productEditForm(@PathVariable Long productId) {
        ProductResponseDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product); // HTTP 200 OK와 함께 상품 정보 반환
    }

    // 6. 상품 수정 (PUT) - /products/{id}
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto request) {
        productService.updateProduct(productId, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // HTTP 204 NO CONTENT
    }

    // 7. 상품 삭제 (Soft Delete) (DELETE) - /products/{id}
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // HTTP 204 NO CONTENT
    }

}