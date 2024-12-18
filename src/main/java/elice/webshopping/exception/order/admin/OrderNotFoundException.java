package elice.webshopping.exception.order.admin;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrderNotFoundException extends InvalidAdminActionException {

    public OrderNotFoundException(Long orderId) {
        super("Order " + orderId + " not found", HttpStatus.BAD_REQUEST);
    }
}
