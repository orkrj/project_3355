package elice.webshopping.exception.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceCustomException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ServiceCustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
