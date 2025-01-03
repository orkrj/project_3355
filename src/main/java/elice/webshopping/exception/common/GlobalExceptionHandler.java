package elice.webshopping.exception.common;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import elice.webshopping.domain.error.ErrorResponseDto;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //@Valid 검증 실패 시 MethodArgumentNotValidException이 발생.
    ///카테고리 생성 및 수정시 유효성 검증
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

//        Map<String, String> errors = new HashMap<>(); //여러 필드 유효성 검사처리
//        bindingResult.getFieldErrors().forEach(error ->
//                errors.put(error.getField(), error.getDefaultMessage())
//        );
//         return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

        // ErrorResponseDto 생성
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "유효성 검증 실패", // error
                bindingResult.getFieldError().getDefaultMessage() // errorMessage
        );

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }




    // JSON 파싱 에러
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleJsonParseException(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        ErrorResponseDto errorResponseDto;

        // 타입 에러 (InvalidFormatException) 처리
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) cause;

            String fieldName = invalidFormatException.getPath().stream()
                    .findFirst()
                    .map(ref -> ref.getFieldName())
                    .orElse("unknown");

            String invalidValue = invalidFormatException.getValue().toString();
            String expectedType = invalidFormatException.getTargetType().getSimpleName();

            errorResponseDto = new ErrorResponseDto(

                    "입력한 값 '" + invalidValue + "'은(는) 유효하지 않습니다. '" + fieldName + "' 필드는 " + expectedType
                            + " 형식이어야 합니다."
                    , ex.getMessage()
            );
        } else {
            // JSON 파싱 에러 (타입 에러가 아닌 경우)
            errorResponseDto = new ErrorResponseDto(
                    "JSON 파싱 형식이 올바르지 않습니다.",
                    ex.getMessage()
            );
        }

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(NoContentsException.class)
    public ResponseEntity<Object> handleNoContentsException(NoContentsException e) {
        log.error("NoContentsException: {}", e.getMessage());
        getStackTraceElement(e);
        return ResponseEntity.ok(Collections.emptyList());
    }

    @ExceptionHandler(ServiceCustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(ServiceCustomException e) {
        log.error("CustomException: {}", e.getMessage());
        getStackTraceElement(e);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "비즈니스 에러 발생",
                e.getMessage()
        );

        return new ResponseEntity<>(errorResponseDto, e.getHttpStatus());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponseDto> handleNullPointerException(NullPointerException e) {
        log.error("NullPointerException: {}", e.getMessage());
        getStackTraceElement(e);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "NPE",
                e.getMessage()
        );

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage());
        getStackTraceElement(e);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                "기타 서버 내부 오류",
                e.getMessage()
        );

        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void getStackTraceElement(Exception e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();

        for (StackTraceElement stackTraceElement : stackTraceElements) {
            if (stackTraceElement.getClassName().startsWith("elice.webshopping")) {
                log.info("Stack trace: {}.{} ({}:{})",
                        stackTraceElement.getClassName(),
                        stackTraceElement.getMethodName(),
                        stackTraceElement.getFileName(),
                        stackTraceElement.getLineNumber());

                return;
            }
        }

        log.error("Stack trace: {}", stackTraceElements[0].getClassName());
    }
}
