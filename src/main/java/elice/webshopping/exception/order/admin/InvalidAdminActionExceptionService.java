package elice.webshopping.exception.order.admin;

import elice.webshopping.exception.common.ServiceCustomException;
import org.springframework.http.HttpStatus;

public class InvalidAdminActionExceptionService extends ServiceCustomException {

    public InvalidAdminActionExceptionService(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
