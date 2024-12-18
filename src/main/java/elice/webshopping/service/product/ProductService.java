package elice.webshopping.service.product;

import elice.webshopping.domain.product.*;
import elice.webshopping.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageService productImageService;

    // 1. 상품 목록 조회 (Read All)
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findByDeletedAtIsNull();
        return products.stream()
                .map(this::convertToProductResponseDto)
                .collect(Collectors.toList());
    }

    // 2. 상품 단건 조회 (Read)
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findByProductIdAndDeletedAtIsNull(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return convertToProductResponseDto(product);
    }

    // 3. 상품 등록 (Create)
    public void createProduct(ProductRequestDto request, ProductImageRequestDto imageRequestDto) {
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .stockQuantity(request.getStockQuantity())
                .build();

        //이미지 파일 처리
        productImageService.addProductImagesWithFiles(product, imageRequestDto);
        productRepository.save(product);
    }

    // 4. 상품 수정 (Update)
    public void updateProduct(Long productId, ProductRequestDto request, ProductImageRequestDto imageRequestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // 상품 정보 업데이트
        product.update(request.getName(), request.getPrice(), request.getDescription(), request.getStockQuantity());

        // 기존 이미지 대체
        productImageService.updateProductImages(product, imageRequestDto);

        // 상품 저장
        productRepository.save(product);
    }

    // 5. 상품 삭제 (Soft Delete)
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Soft delete 처리: deletedAt 값을 현재 시간으로 설정
        productRepository.softDeleteProduct(productId, LocalDateTime.now());
    }

    // 헬퍼 메서드: Product -> ProductResponseDto 변환
    private ProductResponseDto convertToProductResponseDto(Product product) {
        List<String> mainImageUrls = product.getImages().stream()
                .filter(image -> image.getImageType() == ProductImage.ImageType.MAIN)
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());

        List<String> descriptionImageUrls = product.getImages().stream()
                .filter(image -> image.getImageType() == ProductImage.ImageType.DESCRIPTION)
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toList());

        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .mainImageUrls(mainImageUrls)
                .descriptionImageUrls(descriptionImageUrls)
                .build();
    }

}
