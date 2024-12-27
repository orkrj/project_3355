package elice.webshopping.service.productOrder;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.product.Product;
import elice.webshopping.domain.productOrder.ProductOrder;
import elice.webshopping.domain.productOrder.ProductOrderRequestDto;
import elice.webshopping.exception.order.ProductOrderNotExistException;
import elice.webshopping.repository.productOrder.ProductOrderRepository;
import elice.webshopping.service.order.OrderService;
import elice.webshopping.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductOrderServiceImpl implements ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final ProductService productService;
    private final OrderService orderService;

    @Override
    public void createProductOrder(ProductOrderRequestDto productOrderRequestDto) {
        Product product = productService.getProductEntityById(productOrderRequestDto.productId());
        Order order = orderService.getOrderEntityById(productOrderRequestDto.orderId());

        productOrderRepository.saveAndFlush(ProductOrder.of(productOrderRequestDto, product, order));
    }

    @Override
    public List<ProductOrder> getProductOrdersByOrderId(Order order) {
        return productOrderRepository.findAllByOrderId(order.getOrderId())
                .stream()
                .map(productOrder -> productOrder.orElseThrow(ProductOrderNotExistException::new))
                .toList();

    }
}
