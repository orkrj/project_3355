package elice.webshopping.exception.order.user;

import elice.webshopping.exception.common.ServiceCustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidOrderStateException extends ServiceCustomException {

    public InvalidOrderStateException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
