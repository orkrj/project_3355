package elice.webshopping.exception.order.user;

import elice.webshopping.exception.common.ServiceCustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidOrderStateExceptionService extends ServiceCustomException {

    public InvalidOrderStateExceptionService(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
