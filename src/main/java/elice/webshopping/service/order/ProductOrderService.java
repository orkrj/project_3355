package elice.webshopping.service.order;

import elice.webshopping.domain.productOrder.ProductOrderRequestDto;
import elice.webshopping.domain.productOrder.ProductOrderResponseDto;

public interface ProductOrderService {

    ProductOrderResponseDto createProductOrder(ProductOrderRequestDto productOrderRequestDto);
}
