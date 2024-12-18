package elice.webshopping.controller.product;

import elice.webshopping.domain.product.ProductImageRequestDto;
import elice.webshopping.domain.product.Product;
import elice.webshopping.repository.product.ProductRepository;
import elice.webshopping.service.product.ProductImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product/image")
public class ProductImageController {

    private final ProductRepository productRepository;
    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService, ProductRepository productRepository) {
        this.productImageService = productImageService;
        this.productRepository = productRepository;
    }

    // 1. 상품 이미지 등록 (POST) - /product/image/upload
    @PostMapping("/upload")
    public ResponseEntity<Void> uploadProductImages(
            @RequestParam Long productId,
            @RequestParam List<MultipartFile> mainImageFiles,
            @RequestParam List<MultipartFile> descriptionImageFiles) {

        // 상품 조회
        Product product = productRepository.findByProductIdAndDeletedAtIsNull(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // ProductImageRequestDto 생성
        ProductImageRequestDto imageRequestDto = new ProductImageRequestDto(mainImageFiles, descriptionImageFiles);

        // 상품 이미지 추가
        productImageService.addProductImagesWithFiles(product, imageRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build(); // HTTP 201 CREATED
    }

    // 2. 상품 이미지 삭제 (DELETE) - /product/image/{imageId}
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Long imageId) {
        productImageService.deleteProductImage(imageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // HTTP 204 NO CONTENT
    }
}
