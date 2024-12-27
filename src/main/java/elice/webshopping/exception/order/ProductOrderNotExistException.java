package elice.webshopping.exception.order;

import elice.webshopping.exception.common.ServiceCustomException;
import org.springframework.http.HttpStatus;

public class ProductOrderNotExistException extends ServiceCustomException {

    public ProductOrderNotExistException() {
        super("ProductOrder not exist!", HttpStatus.BAD_REQUEST);
    }

    public ProductOrderNotExistException(Long orderId) {
        super("ProductOrder with " + orderId + " not exist", HttpStatus.BAD_REQUEST);
    }
}
