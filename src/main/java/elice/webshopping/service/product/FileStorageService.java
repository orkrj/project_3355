package elice.webshopping.service.product;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;

@Service
public class FileStorageService {

    private final AmazonS3 amazonS3;
    private final String bucketName;

    public FileStorageService(AmazonS3 amazonS3, @Value("${spring.cloud.aws.s3.bucket}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    // 업로드용 Signed URL 생성
    public URL generateUploadSignedUrl(String key, int expirationMinutes) {
        return generateSignedUrl(key, expirationMinutes, HttpMethod.PUT);
    }

    // 다운로드용 Signed URL 생성
    public URL generateDownloadSignedUrl(String key, int expirationMinutes) {
        return generateSignedUrl(key, expirationMinutes, HttpMethod.GET);
    }

    // 공통 Signed URL 생성 로직
    private URL generateSignedUrl(String key, int expirationMinutes, HttpMethod method) {
        Date expiration = new Date(System.currentTimeMillis() + (long) expirationMinutes * 60 * 1000);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key)
                .withMethod(method)
                .withExpiration(expiration);
        return amazonS3.generatePresignedUrl(request);
    }
}