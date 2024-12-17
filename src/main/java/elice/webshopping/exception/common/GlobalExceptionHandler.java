package elice.webshopping.exception.common;

import elice.webshopping.domain.error.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceCustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(ServiceCustomException e) {
        log.error("CustomException: {}", e.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "비즈니스 에러 발생",
                e.getMessage()
        );

        return new ResponseEntity<>(errorResponseDto, e.getHttpStatus());
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDto> handleNullPointerException(NullPointerException e) {
        log.error("NullPointerException: {}", e.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "NPE",
                e.getMessage()
        );

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "기타 서버 내부 오류",
                e.getMessage()
        );

        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
