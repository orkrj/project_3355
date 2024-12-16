package elice.webshopping.exception.order.user;

import elice.webshopping.domain.order.OrderStatus;

public class OrderReadyForShippingException extends InvalidOrderStateException{

    public OrderReadyForShippingException(OrderStatus orderStatus, String orderNumber) {
        super(
                orderStatus,
                "The order: " + orderNumber + " is already ready for shipping and cannot be modified."
        );
    }
}
