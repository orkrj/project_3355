//package elice.webshopping.service.order;
//
//import elice.webshopping.domain.order.Order;
//import elice.webshopping.domain.product.Product;
//import elice.webshopping.domain.productOrder.ProductOrder;
//import elice.webshopping.domain.productOrder.ProductOrderRequestDto;
//import elice.webshopping.domain.productOrder.ProductOrderResponseDto;
//import elice.webshopping.repository.order.ProductOrderRepository;
//import elice.webshopping.service.product.ProductService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ProductOrderServiceImpl implements ProductOrderService {
//
//    private final ProductOrderRepository productOrderRepository;
//    private final ProductService productService;
//    private final OrderService orderService;
//
//    @Override
//    public ProductOrderResponseDto createProductOrder(ProductOrderRequestDto productOrderRequestDto) {
//        Product product = productService.getProductEntityById(productOrderRequestDto.productId());
//        Order order = orderService.getOrderEntityById(productOrderRequestDto.orderId());
//
//        return ProductOrderResponseDto.from(
//                productOrderRepository.save(ProductOrder.of(productOrderRequestDto, product, order))
//        );
//    }
//}
