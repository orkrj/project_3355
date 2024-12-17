package elice.webshopping.exception.order;

import elice.webshopping.exception.common.ServiceCustomException;
import org.springframework.http.HttpStatus;

public class ReceiverNotExistException extends ServiceCustomException {

    public ReceiverNotExistException(Long receiverId) {
        super("Receiver " + receiverId + " not exist", HttpStatus.BAD_REQUEST);
    }
}
