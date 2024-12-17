package elice.webshopping.exception.order;

import elice.webshopping.exception.common.ServiceCustomException;
import org.springframework.http.HttpStatus;

public class ReceiverNotExistExceptionService extends ServiceCustomException {

    public ReceiverNotExistExceptionService(Long receiverId) {
        super("Receiver " + receiverId + " not exist", HttpStatus.BAD_REQUEST);
    }
}
