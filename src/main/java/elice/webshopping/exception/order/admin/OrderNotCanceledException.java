package elice.webshopping.exception.order.admin;

public class OrderNotCanceledException extends InvalidAdminActionException{

    public OrderNotCanceledException(Long orderId) {
        super("Order " + orderId + " not canceled yet");
    }
}
