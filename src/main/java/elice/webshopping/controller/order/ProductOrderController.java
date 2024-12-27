package elice.webshopping.controller.order;

import elice.webshopping.domain.productOrder.ProductOrder;
import elice.webshopping.domain.productOrder.ProductOrderRequestDto;
import elice.webshopping.domain.productOrder.ProductOrderResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productOrder")
public class ProductOrderController {

    public ResponseEntity<ProductOrderResponseDto> createProductOrder(ProductOrderRequestDto productOrderRequestDto) {

    }
}
