package elice.webshopping.service.productOrder;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.productOrder.ProductOrder;
import elice.webshopping.domain.productOrder.ProductOrderRequestDto;
import elice.webshopping.domain.productOrder.ProductOrderResponseDto;

import java.util.List;

public interface ProductOrderService {

    ProductOrderResponseDto createProductOrder(ProductOrderRequestDto productOrderRequestDto);

    List<ProductOrder> getProductOrdersByOrderId(Order order);
}
