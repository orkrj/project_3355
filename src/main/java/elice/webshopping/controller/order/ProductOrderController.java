package elice.webshopping.controller.order;

import elice.webshopping.domain.productOrder.ProductOrderRequestDto;
import elice.webshopping.service.productOrder.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/productOrder")
@RequiredArgsConstructor
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    @PostMapping
    public ResponseEntity<Void> createProductOrder(ProductOrderRequestDto productOrderRequestDto) {
        productOrderService.createProductOrder(productOrderRequestDto);

        return ResponseEntity.ok().build();
    }

    // productOrders 호출은 서버 내부용으로 사용할 예정이라 (Order 에 할당용) 컨트롤러 필요 없을듯?
}
