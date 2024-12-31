package elice.webshopping.controller.product;

import elice.webshopping.domain.product.ProductImageRequestDto;
import elice.webshopping.domain.product.ProductRequestDto;
import elice.webshopping.domain.product.ProductResponseDto;
import elice.webshopping.service.product.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/product")
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

    // 1-1. 상품 전체 조회 (GET) - /api/product/page
    @GetMapping("/page")
    public ResponseEntity<Page<ProductResponseDto>> getPagedProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<ProductResponseDto> pagedProducts = productService.getPagedProducts(pageable);
        return ResponseEntity.ok(pagedProducts);
    }

    // 1-2. 상품명으로 조회 (GET) - /api/product/search
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDto>> searchProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<ProductResponseDto> products = productService.searchProducts(name, pageable);
        return ResponseEntity.ok(products);
    }

    // 1-3. 카테고리 이름으로 조회 (GET) - /api/product/category/{categoryName}
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Page<ProductResponseDto>> getProductsByCategoryName(
            @PathVariable String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy));
        Page<ProductResponseDto> products = productService.getProductsByCategoryName(categoryName, pageable);

        return ResponseEntity.ok(products);
    }

    // 2. 상품 단건 조회 (GET) - /product/{productId}
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        ProductResponseDto product = productService.getProductById(productId);
        return ResponseEntity.ok(product); // HTTP 200 OK
    }

    // 3. 상품 생성 (POST) - /product
    @PostMapping
    public ResponseEntity<List<URL>> createProduct(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "categoryId") Long categoryId,  // 카테고리 ID 추가
            @RequestParam(value = "price") int price,
            @RequestParam(value = "stockQuantity") int stockQuantity,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "mainImageFiles", required = false) List<MultipartFile> mainImageFiles,
            @RequestParam(value = "descriptionImageFiles", required = false) List<MultipartFile> descriptionImageFiles) {

        ProductRequestDto request = new ProductRequestDto(name, price, description, stockQuantity, categoryId);
        ProductImageRequestDto imageRequestDto = new ProductImageRequestDto(mainImageFiles, descriptionImageFiles);

        List<URL> signedUrls = productService.createProduct(request, imageRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(signedUrls);
    }

    // 4. 상품 수정 (PUT) - /product/{productId}
    @PutMapping("/{productId}")
    public ResponseEntity<List<URL>> updateProduct(
            @PathVariable Long productId,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "categoryId") Long categoryId,  // 카테고리 ID 추가
            @RequestParam(value = "price") int price,
            @RequestParam(value = "stockQuantity") int stockQuantity,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "mainImageFiles", required = false) List<MultipartFile> mainImageFiles,
            @RequestParam(value = "descriptionImageFiles", required = false) List<MultipartFile> descriptionImageFiles) {

        ProductRequestDto request = new ProductRequestDto(name, price, description, stockQuantity, categoryId);
        ProductImageRequestDto imageRequestDto = new ProductImageRequestDto(mainImageFiles, descriptionImageFiles);

        List<URL> signedUrls = productService.updateProduct(productId, request, imageRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(signedUrls);
    }

    // 5. 상품 삭제 (Soft Delete) (DELETE) - /product/{productId}
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // HTTP 204 NO CONTENT
    }

}