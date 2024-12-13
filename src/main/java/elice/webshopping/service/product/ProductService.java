package elice.webshopping.service.product;

import elice.webshopping.domain.product.Product;
import elice.webshopping.domain.product.ProductImage;
import elice.webshopping.domain.product.ProductRequestDto;
import elice.webshopping.domain.product.ProductResponseDto;
import elice.webshopping.repository.product.ProductImageRepository;
import elice.webshopping.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    // 1. 상품 목록 조회 (Read All)
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findByDeletedAtIsNull(); // 삭제된 상품 제외
        return products.stream()
                .map(this::convertToProductResponseDto)
                .collect(Collectors.toList());
    }

    // 2. 상품 단건 조회 (Read)
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(productId) // 삭제된 상품 제외
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return convertToProductResponseDto(product);
    }

    // 3. 상품 등록 (Create)
    public void createProduct(ProductRequestDto request) {
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .stockQuantity(request.getStockQuantity())
                .build();

        // 메인 이미지 추가
        for (String url : request.getMainImageUrls()) {
            ProductImage mainImage = ProductImage.builder()
                    .imageUrl(url)
                    .imageType(ProductImage.ImageType.MAIN)
                    .product(product)
                    .build();
            product.addImage(mainImage);
        }

        // 상세 이미지 추가
        for (String url : request.getDescriptionImageUrls()) {
            ProductImage descriptionImage = ProductImage.builder()
                    .imageUrl(url)
                    .imageType(ProductImage.ImageType.DESCRIPTION)
                    .product(product)
                    .build();
            product.addImage(descriptionImage);
        }

        productRepository.save(product);
    }

    // 4. 상품 수정 (Update)
    public void updateProduct(Long productId, ProductRequestDto request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setStockQuantity(request.getStockQuantity());

        // 기존 이미지 제거 및 새로운 이미지 추가
        product.getImages().clear();

        for (String url : request.getMainImageUrls()) {
            ProductImage mainImage = ProductImage.builder()
                    .imageUrl(url)
                    .imageType(ProductImage.ImageType.MAIN)
                    .product(product)
                    .build();
            product.addImage(mainImage);
        }

        for (String url : request.getDescriptionImageUrls()) {
            ProductImage descriptionImage = ProductImage.builder()
                    .imageUrl(url)
                    .imageType(ProductImage.ImageType.DESCRIPTION)
                    .product(product)
                    .build();
            product.addImage(descriptionImage);
        }

        productRepository.save(product);
    }

    // 5. 상품 삭제 (Delete)
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        productRepository.delete(product);
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
