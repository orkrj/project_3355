package elice.webshopping.controller.product;

import elice.webshopping.service.product.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileStorageService fileStorageService;

    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/signed-url/upload")
    public ResponseEntity<String> getUploadSignedUrl(
            @RequestParam String key,
            @RequestParam int expirationMinutes) {
        URL signedUrl = fileStorageService.generateUploadSignedUrl(key, expirationMinutes);
        return ResponseEntity.ok(signedUrl.toString());
    }

    @GetMapping("/signed-url/download")
    public ResponseEntity<String> getDownloadSignedUrl(
            @RequestParam String key,
            @RequestParam int expirationMinutes) {
        URL signedUrl = fileStorageService.generateDownloadSignedUrl(key, expirationMinutes);
        return ResponseEntity.ok(signedUrl.toString());
    }
}
