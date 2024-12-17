package elice.webshopping.exception.order.user;

import elice.webshopping.domain.order.OrderStatus;
import elice.webshopping.exception.common.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidOrderStateException extends CustomException {

    public InvalidOrderStateException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
