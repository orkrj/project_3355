package elice.webshopping.service.product;

import elice.webshopping.domain.product.Product;
import elice.webshopping.domain.product.ProductImage;
import elice.webshopping.repository.product.ProductImageRepository;
import elice.webshopping.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    // 상품 이미지 등록
    public void addProductImages(Long productId, List<String> mainImageUrls, List<String> descriptionImageUrls) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // 메인 이미지 추가
        for (String url : mainImageUrls) {
            ProductImage mainImage = ProductImage.createWithProduct(product, url, ProductImage.ImageType.MAIN);
            product.addImage(mainImage);
        }

        // 상세 이미지 추가
        for (String url : descriptionImageUrls) {
            ProductImage descriptionImage = ProductImage.createWithProduct(product, url, ProductImage.ImageType.DESCRIPTION);
            product.addImage(descriptionImage);
        }

        productRepository.save(product); // 변경 사항 저장
    }

    // 상품 이미지 수정
    public void updateProductImage(Long imageId, String newImageUrl, ProductImage.ImageType newImageType) {
        ProductImage productImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Product image not found"));

        productImage.setImageUrl(newImageUrl);
        productImage.setImageType(newImageType);

        productImageRepository.save(productImage); // 변경 사항 저장
    }

    // 상품 이미지 삭제 (ProductImage ID로 삭제)
    public void deleteProductImage(Long imageId) {
        ProductImage productImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Product image not found"));

        productImage.getProduct().removeImage(productImage);
        productImageRepository.delete(productImage); // 이미지 삭제
    }
}
