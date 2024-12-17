package elice.webshopping.exception.order.admin;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrderNotCanceledException extends InvalidAdminActionException {

    public OrderNotCanceledException(Long orderId) {
        super("Order " + orderId + " not canceled yet", HttpStatus.BAD_REQUEST);
    }
}
