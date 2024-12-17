package elice.webshopping.exception.order.admin;

import elice.webshopping.exception.common.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidAdminActionException extends CustomException {

    public InvalidAdminActionException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
