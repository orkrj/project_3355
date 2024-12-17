package elice.webshopping.exception.order;

public class ReceiverNotExistException extends RuntimeException {

    public ReceiverNotExistException(Long receiverId) {
        super("Receiver " + receiverId + " not exist");
    }
}
