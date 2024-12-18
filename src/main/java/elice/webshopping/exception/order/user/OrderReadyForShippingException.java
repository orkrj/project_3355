package elice.webshopping.exception.order.user;

import elice.webshopping.domain.order.OrderStatus;
import org.springframework.http.HttpStatus;

public class OrderReadyForShippingException extends InvalidOrderStateException {

    public OrderReadyForShippingException(OrderStatus orderStatus, String orderNumber) {
        super(
                "주문 번호 " + orderNumber + " 은 " + orderStatus + " 상태이기 때문에 수정할 수 없습니다.",
                HttpStatus.BAD_REQUEST
        );
    }
}
