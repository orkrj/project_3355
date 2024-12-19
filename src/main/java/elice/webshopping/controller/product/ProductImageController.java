package elice.webshopping.controller.product;

import elice.webshopping.domain.product.ProductImageRequestDto;
import elice.webshopping.domain.product.Product;
import elice.webshopping.repository.product.ProductRepository;
import elice.webshopping.service.product.ProductImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/product/image")
public class ProductImageController {
    private final ProductImageService productImageService;
    private final ProductRepository productRepository;

    public ProductImageController(ProductImageService productImageService, ProductRepository productRepository) {
        this.productImageService = productImageService;
        this.productRepository = productRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<List<URL>> generateImageUploadUrls(
            @RequestParam Long productId,
            @RequestParam List<MultipartFile> mainImageFiles,
            @RequestParam List<MultipartFile> descriptionImageFiles) {

        Product product = productRepository.findByProductIdAndDeletedAtIsNull(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        ProductImageRequestDto requestDto = new ProductImageRequestDto(mainImageFiles, descriptionImageFiles);

        List<URL> signedUrls = productImageService.generateImageUploadUrls(product, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(signedUrls);
    }
}
