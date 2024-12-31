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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;


    // Signed URL을 이용하여 이미지 파일 업로드 처리
    public List<URL> generateImageUploadUrls(Product product, ProductImageRequestDto requestDto) {
        List<URL> uploadUrls = new ArrayList<>();

        // main 이미지 타입 처리
        uploadUrls.addAll(generateUrlsForImages(product.getProductId(), "main", requestDto.getMainImageFiles()));
        // description 이미지 타입 처리
        uploadUrls.addAll(generateUrlsForImages(product.getProductId(), "description", requestDto.getDescriptionImageFiles()));

        return uploadUrls;
    }

    private List<URL> generateUrlsForImages(Long productId, String imageType, List<MultipartFile> files) {
        List<URL> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            String key = String.format("products/%s/%s", imageType, file.getOriginalFilename());
            URL signedUrl = fileStorageService.generateUploadSignedUrl(key, 15);
            urls.add(signedUrl);
        }
        return urls;
    }

    // 이미지 메타데이터 저장
    public void saveImageMetadata(Product product, List<URL> imageUrls) {
        int mainImageCount = imageUrls.size() / 2;
        for (int i = 0; i < imageUrls.size(); i++) {
            ProductImage.ImageType imageType = (i < mainImageCount) ? ProductImage.ImageType.MAIN : ProductImage.ImageType.DESCRIPTION;
            ProductImage image = ProductImage.createWithProduct(product, imageUrls.get(i).toString(), imageType);
            product.addImage(image);
        }
    }
}
