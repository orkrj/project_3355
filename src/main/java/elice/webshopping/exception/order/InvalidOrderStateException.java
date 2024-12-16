package elice.webshopping.exception.order;

import elice.webshopping.domain.order.OrderStatus;
import lombok.Getter;

@Getter
public class InvalidOrderStateException extends RuntimeException{

    private final OrderStatus orderStatus;

    public InvalidOrderStateException(OrderStatus orderStatus, String message) {
        super(message);
        this.orderStatus = orderStatus;
    }
}
