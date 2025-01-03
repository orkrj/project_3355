package elice.webshopping.service.product;

import elice.webshopping.domain.category.Category;
import elice.webshopping.domain.product.*;
import elice.webshopping.exception.common.NoContentsException;
import elice.webshopping.repository.category.CategoryRepository;
import elice.webshopping.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final CategoryRepository categoryRepository;

    // 1. 상품 목록 조회 (Read All)
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findByDeletedAtIsNull();

        if (products.isEmpty()) {
            throw new NoContentsException("No products found");
        }
        return products.stream()
                .map(this::convertToProductResponseDto)
                .collect(Collectors.toList());
    }

    // 1-1. 상품 목록 조회 (Read All) -- 페이징 o
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getPagedProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findByDeletedAtIsNull(pageable);

        // DTO로 변환하여 반환
        return productPage.map(this::convertToProductResponseDto);
    }

    // 1-2. 상품명으로 상품 조회
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> searchProducts(String name, Pageable pageable) {
        // 검색 및 정렬 조건으로 상품 검색
        Page<Product> productPage = productRepository.findByNameContainingAndDeletedAtIsNull(name, pageable);
        return productPage.map(this::convertToProductResponseDto);
    }

    // 1-3. 카테고리 이름으로 상품 목록 조회
    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProductsByCategoryName(String categoryName, Pageable pageable) {
        Page<Product> productPage = productRepository.findByCategory_NameAndDeletedAtIsNull(categoryName, pageable);

        if (productPage.isEmpty()) {
            throw new NoContentsException("No products found for the given category name");
        }

        return productPage.map(this::convertToProductResponseDto);
    }

    // 2. 상품 단건 조회 (Read)
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findByProductIdAndDeletedAtIsNull(productId)
                .orElseThrow(() -> new NoContentsException("Product not found with id: " + productId));
        return convertToProductResponseDto(product);
    }

    public Product getProductEntityById(Long productId) {
        return productRepository.findByProductIdAndDeletedAtIsNull(productId)
                .orElseThrow(() -> new NoContentsException("Product not found with id: " + productId));
    }

    // 3. 상품 등록 (Create)
    public List<URL> createProduct(ProductRequestDto request, ProductImageRequestDto imageRequestDto) {
        // 카테고리 조회
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        // 상품 생성
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .stockQuantity(request.getStockQuantity())
                .category(category)  // 카테고리 연관 설정
                .build();

        productRepository.save(product);

        // Signed URL 생성 및 반환
        List<URL> signedUrls = productImageService.generateImageUploadUrls(product, imageRequestDto);

        // 이미지 메타데이터 저장 (DB에 이미지 정보 저장)
        productImageService.saveImageMetadata(product, signedUrls);

        return signedUrls;
    }

    // 4. 상품 수정 (Update)
    public List<URL> updateProduct(Long productId, ProductRequestDto request, ProductImageRequestDto imageRequestDto) {
        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoContentsException("Product not found with id: " + productId));

        // 카테고리 수정
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        product.setCategory(category);  // 카테고리 업데이트

        // 상품 정보 수정
        product.update(request.getName(), request.getPrice(), request.getDescription(), request.getStockQuantity(), category);

        // 기존 이미지 메타데이터 삭제
        product.getImages().clear();

        // Signed URL 생성 및 반환
        List<URL> signedUrls = productImageService.generateImageUploadUrls(product, imageRequestDto);

        // 새 이미지 메타데이터 저장 (DB에 이미지 정보 저장)
        productImageService.saveImageMetadata(product, signedUrls);

        return signedUrls;
    }

    // 5. 상품 삭제 (Soft Delete)
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoContentsException("Product not found with id: " + productId));

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

        // 카테고리 이름 가져오기
        Long categoryId = product.getCategory() != null ? product.getCategory().getId() : null;
        String categoryName = product.getCategory() != null ? product.getCategory().getName() : null;

        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .stockQuantity(product.getStockQuantity())
                .categoryId(categoryId)
                .categoryName(categoryName)  // 카테고리 이름 설정
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .mainImageUrls(mainImageUrls)
                .descriptionImageUrls(descriptionImageUrls)
                .build();
    }

    //Category 이름으로 Product 찾기
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findProductBy(String categoryName) {
        List<Product> products = productRepository.findProductBy(categoryName);
        return products.stream()
                .map(this::convertToProductResponseDto)
                .collect(Collectors.toList());
    }
}
