package elice.webshopping.exception.order.address;

import elice.webshopping.exception.common.ServiceCustomException;
import org.springframework.http.HttpStatus;

public class AddressNotFoundException extends ServiceCustomException {
    public AddressNotFoundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}