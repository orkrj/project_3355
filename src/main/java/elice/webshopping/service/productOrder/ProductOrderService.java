package elice.webshopping.service.productOrder;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.productOrder.ProductOrder;
import elice.webshopping.domain.productOrder.ProductOrderRequestDto;

import java.util.List;

public interface ProductOrderService {

    void createProductOrder(ProductOrderRequestDto productOrderRequestDto);

    List<ProductOrder> getProductOrdersByOrderId(Order order);
}
