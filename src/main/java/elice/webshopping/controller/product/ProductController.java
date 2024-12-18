package elice.webshopping.controller.product;

import elice.webshopping.domain.product.ProductImageRequestDto;
import elice.webshopping.domain.product.ProductRequestDto;
import elice.webshopping.domain.product.ProductResponseDto;
import elice.webshopping.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. 상품 전체 조회 (GET) - /product
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products); // HTTP 200 OK
    }

    // 2. 상품 단건 조회 (GET) - /product/{productId}
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        ProductResponseDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product); // HTTP 200 OK
    }

    // 3. 상품 생성 (POST) - /product
    @PostMapping
    public ResponseEntity<Void> createProduct(
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("description") String description,
            @RequestParam("stockQuantity") int stockQuantity,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "mainImageFiles", required = false) List<MultipartFile> mainImageFiles,
            @RequestParam(value = "descriptionImageFiles", required = false) List<MultipartFile> descriptionImageFiles) {

        // ProductRequestDto 객체 생성
        ProductRequestDto request = new ProductRequestDto(name, price, description, stockQuantity, categoryId);

        // 이미지 파일 처리
        ProductImageRequestDto imageRequestDto = new ProductImageRequestDto(mainImageFiles, descriptionImageFiles);

        // 서비스 호출
        productService.createProduct(request, imageRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build(); // HTTP 201 CREATED
    }

    // 4. 상품 수정 (PUT) - /product/{productId}
    @PutMapping("/{productId}")
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long productId,
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("description") String description,
            @RequestParam("stockQuantity") int stockQuantity,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam List<MultipartFile> mainImageFiles,
            @RequestParam List<MultipartFile> descriptionImageFiles) {

        // ProductRequestDto 객체 생성
        ProductRequestDto request = new ProductRequestDto(name, price, description, stockQuantity, categoryId);

        // 이미지 파일 처리
        ProductImageRequestDto imageRequestDto = new ProductImageRequestDto(mainImageFiles, descriptionImageFiles);

        // 서비스 호출
        productService.updateProduct(productId, request, imageRequestDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // HTTP 204 NO CONTENT
    }

    // 5. 상품 삭제 (Soft Delete) (DELETE) - /product/{productId}
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // HTTP 204 NO CONTENT
    }

}