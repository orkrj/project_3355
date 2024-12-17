package elice.webshopping.exception.order;

import elice.webshopping.exception.common.CustomException;
import org.springframework.http.HttpStatus;

public class ReceiverNotExistException extends CustomException {

    public ReceiverNotExistException(Long receiverId) {
        super("Receiver " + receiverId + " not exist", HttpStatus.BAD_REQUEST);
    }
}
