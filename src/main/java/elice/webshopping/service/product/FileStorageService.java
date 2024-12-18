package elice.webshopping.service.product;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    public String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty or null");
        }

        try {
            // 고유 파일 이름 생성
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                throw new IllegalArgumentException("File name is null");
            }

            validateFileExtension(originalFilename);
            String uniqueFileName = UUID.randomUUID() + "_" + originalFilename;

            // S3로 파일 업로드
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);

            // S3 URL 생성
            return amazonS3.getUrl(bucketName, uniqueFileName).toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    // 파일 확장자 검증
    private void validateFileExtension(String filename) {
        String[] allowedExtensions = {"jpg", "jpeg", "png", "gif"};
        String extension = getFileExtension(filename).toLowerCase();

        if (Arrays.stream(allowedExtensions).noneMatch(extension::equals)) {
            throw new IllegalArgumentException("Unsupported file type: " + extension);
        }
    }

    // 파일 확장자 추출
    private String getFileExtension(String filename) {
        int lastIndex = filename.lastIndexOf('.');
        if (lastIndex == -1 || lastIndex == filename.length() - 1) {
            throw new IllegalArgumentException("File does not have a valid extension: " + filename);
        }
        return filename.substring(lastIndex + 1);
    }
}
