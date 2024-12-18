package elice.webshopping.exception.order.admin;

import elice.webshopping.exception.common.ServiceCustomException;
import org.springframework.http.HttpStatus;

public class InvalidAdminActionException extends ServiceCustomException {

    public InvalidAdminActionException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
