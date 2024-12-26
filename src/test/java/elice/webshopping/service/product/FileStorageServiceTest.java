package elice.webshopping.service.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileStorageServiceTest {

    @Autowired
    private FileStorageService fileStorageService;

//    @Test
//    public void testUploadFile() {
//        MockMultipartFile mockFile = new MockMultipartFile(
//                "file",
//                "test-image.jpg",
//                "image/jpeg",
//                "Dummy content".getBytes()
//        );
//
//        String uploadedUrl = fileStorageService.saveFile(mockFile);
//        assertNotNull(uploadedUrl);
//        System.out.println("Uploaded URL: " + uploadedUrl);
//    }
}