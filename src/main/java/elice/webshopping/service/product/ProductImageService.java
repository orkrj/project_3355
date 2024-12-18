package elice.webshopping.service.product;

import elice.webshopping.domain.product.Product;
import elice.webshopping.domain.product.ProductImage;
import elice.webshopping.domain.product.ProductImageRequestDto;
import elice.webshopping.repository.product.ProductImageRepository;
import elice.webshopping.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

    // 상품 이미지 파일 업로드 및 저장
    public void addProductImagesWithFiles(Product product, ProductImageRequestDto requestDto) {
        // 메인 이미지 업로드 및 저장
        for (MultipartFile file : requestDto.getMainImageFiles()) {
            String imageUrl = fileStorageService.saveFile(file);
            ProductImage mainImage = ProductImage.createWithProduct(product, imageUrl, ProductImage.ImageType.MAIN);
            product.addImage(mainImage);
        }

        // 상세 이미지 업로드 및 저장
        for (MultipartFile file : requestDto.getDescriptionImageFiles()) {
            String imageUrl = fileStorageService.saveFile(file);
            ProductImage descriptionImage = ProductImage.createWithProduct(product, imageUrl, ProductImage.ImageType.DESCRIPTION);
            product.addImage(descriptionImage);
        }
    }

    // 상품 이미지 업데이트
    public void updateProductImages(Product product, ProductImageRequestDto requestDto) {
        // 기존 이미지 삭제
        List<ProductImage> existingImages = product.getImages();
        productImageRepository.deleteAll(existingImages);
        product.getImages().clear();

        // 새 이미지 추가
        addProductImagesWithFiles(product, requestDto);
    }

    // 상품 이미지 삭제 (이미지 ID로)
    public void deleteProductImage(Long imageId) {
        ProductImage productImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Image not found"));

        productImageRepository.delete(productImage);
    }
}
