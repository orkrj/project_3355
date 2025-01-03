package elice.webshopping.controller.order;

import elice.webshopping.domain.productOrder.ProductOrderRequestDto;
import elice.webshopping.domain.productOrder.ProductOrderResponseDto;
import elice.webshopping.service.productOrder.ProductOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productOrder")
@RequiredArgsConstructor
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    @PostMapping
    public ResponseEntity<ProductOrderResponseDto> createProductOrder(@RequestBody ProductOrderRequestDto productOrderRequestDto) {
        return ResponseEntity.ok(productOrderService.createProductOrder(productOrderRequestDto));
    }
}
