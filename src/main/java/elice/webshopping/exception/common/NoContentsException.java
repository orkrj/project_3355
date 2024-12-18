package elice.webshopping.exception.common;

import org.springframework.http.HttpStatus;

public class NoContentsException extends ServiceCustomException {

    public NoContentsException(String message) {
        super(
                message,
                HttpStatus.NO_CONTENT
        );
    }
}

