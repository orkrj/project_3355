package elice.webshopping.controller.product;

import elice.webshopping.domain.product.ProductImageRequestDto;
import elice.webshopping.service.product.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product/{productId}/images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    // 1. 상품 이미지 추가 (POST) - /product/{productId}/images
    @PostMapping
    public ResponseEntity<Void> addProductImages(
            @PathVariable Long productId,
            @RequestBody @Valid ProductImageRequestDto request) {

        productImageService.addProductImages(productId, request.getMainImageUrls(), request.getDescriptionImageUrls());
        return ResponseEntity.status(HttpStatus.CREATED).build(); // HTTP 201 CREATED
    }

    // 2. 상품 이미지 삭제 (DELETE) - /product/{productId}/images/{imageId}
    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteProductImage(@PathVariable Long imageId) {
        productImageService.deleteProductImage(imageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // HTTP 204 NO CONTENT
    }

    // 3. 상품 이미지 수정 (PUT) - /product/{productId}/images/{imageId}
    @PutMapping("/{imageId}")
    public ResponseEntity<Void> updateProductImage(
            @PathVariable Long imageId,
            @RequestBody @Valid ProductImageRequestDto request) {

        productImageService.updateProductImage(imageId, request.getImageUrl(), request.getImageType());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // HTTP 204 NO CONTENT
    }
}
