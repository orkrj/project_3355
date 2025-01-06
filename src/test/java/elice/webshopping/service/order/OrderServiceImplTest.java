package elice.webshopping.service.order;

import elice.webshopping.domain.order.*;
import elice.webshopping.domain.payment.Payment;
import elice.webshopping.domain.product.Product;
import elice.webshopping.domain.productOrder.ProductOrder;
import elice.webshopping.domain.productOrder.ProductOrderRequestDto;
import elice.webshopping.domain.user.User;
import elice.webshopping.exception.common.NoContentsException;
import elice.webshopping.exception.order.admin.OrderNotCanceledException;
import elice.webshopping.exception.order.user.OrderReadyForShippingException;
import elice.webshopping.repository.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

//    @Test
//    @DisplayName("주문 전체 조회: 삭제되지 않은 주문만")
//    void shouldReturnAllNonDeletedOrders() {
//
//        // given
//        List<Order> orders = givenOrders();
//        given(orderRepository.findAll()).willReturn(orders);
//
//        // when
//        List<OrderResponseDto> findOrders = orderService.getOrders();
//
//        // then
//        assertEquals(2, findOrders.size(), "소프트 딜리트된 test 3 은 조회되면 안 됨");
//        assertEquals("202412161200000001", findOrders.get(0).orderNumber());
//        verify(orderRepository, times(1)).findAll();
//    }

    @Test
    @DisplayName("주문 단건 조회: 삭제되지 않은 경우")
    void shouldReturnNonDeletedOrderEntityById() {

        // given
        Order order = givenOrder(false, true);
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        // when
        Order findOrder = orderService.getOrderEntityById(1L);

        // then
        assertEquals(1, findOrder.getOrderId());
        assertEquals("202412161200000001", findOrder.getOrderNumber());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 단건 조회: 삭제된 경우 예외 발생")
    void shouldThrowException_whenGetDeletedOrderEntityById() {

        // given
        Order order = givenOrder(true, false);
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        // when, then
        assertThrows(NoContentsException.class, () -> orderService.getOrderEntityById(1L));
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 취소: 주문 취소 가능, 조회시 예외 발생")
    void shouldSetDeletedAt_whenCancelOrder() {

        // given
        Order order = givenOrder(false, true);
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        // when
        orderService.cancelOrder(1L);

        // then
        assertNotNull(order.getDeletedAt());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 취소: 주문 취소 불가능, 예외 발생")
    void shouldThrowException_whenOrderCanNotBeDeleted() {
        // given
        Order order = givenOrder(false, false);
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        // when, then
        assertThrows(OrderReadyForShippingException.class, () -> orderService.cancelOrder(1L));
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 삭제: 주문 삭제 가능")
    void shouldBeDeleted_whenDeleteOrderById() {

        // given
        Order order = givenOrder(true, true);
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        // when
        orderService.deleteOrderById(1L);

        // then
        assertThrows(NoContentsException.class, () -> orderService.getOrderEntityById(1L));
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("주문 삭제: 주문 취소 전, 주문 삭제 불가능")
    void shouldThrowException_whenDeletedAtIsNull() {

        // given
        Order order = givenOrder(false, true);
        given(orderRepository.findById(1L)).willReturn(Optional.of(order));

        // when, then
        assertThrows(OrderNotCanceledException.class, () -> orderService.deleteOrderById(1L));
        verify(orderRepository, times(0)).deleteById(1L);
    }

    private User givenMockUser() {
        return Mockito.mock(User.class);
    }

    private Receiver givenMockReceiver() {
        Receiver mockReceiver = Mockito.mock(Receiver.class);
        Mockito.when(mockReceiver.getName()).thenReturn("mockReceiverName");
        Mockito.when(mockReceiver.getPhoneNumber()).thenReturn("01012345678");
        Mockito.when(mockReceiver.getZipCode()).thenReturn("00000");
        Mockito.when(mockReceiver.getStreetAddress()).thenReturn("Seonggyungwan-ro");
        Mockito.when(mockReceiver.getDetailAddress()).thenReturn("25-2");

        return mockReceiver;
    }

    private ReceiverRequestDto givenReceiverRequestDto() {
        return new ReceiverRequestDto (
                "mockReceiverName",
                "01012345678",
                "00000",
                "Seonggyungwan-ro",
                "25-2"
        );
    }

    private ProductOrder givenMockProductOrder() {
        return Mockito.mock(ProductOrder.class);
    }

    private ProductOrder givenMockProductOrderWithMockProduct(Long id) {
        ProductOrder mockProductOrder = Mockito.mock(ProductOrder.class);
        Product mockProduct = Mockito.mock(Product.class);

        Mockito.when(mockProductOrder.getProduct()).thenReturn(mockProduct);
        Mockito.when(mockProduct.getProductId()).thenReturn(id);

        return mockProductOrder;
    }

    private List<ProductOrder> givenMockProductOrders() {
        List<ProductOrder> mockProductOrders = new ArrayList<>();
        mockProductOrders.add(givenMockProductOrderWithMockProduct(1L));
        mockProductOrders.add(givenMockProductOrderWithMockProduct(2L));
        mockProductOrders.add(givenMockProductOrderWithMockProduct(3L));

        return mockProductOrders;
    }

    private List<ProductOrderRequestDto> givenProductOrdersRequestDto() {
        return List.of(
                new ProductOrderRequestDto(1L, 1L, 500000, 1),
                new ProductOrderRequestDto(2L, 2L, 2000, 2),
                new ProductOrderRequestDto(3L, 3L, 3000, 3)
        );
    }

    private List<Order> givenOrders() {
        User mockUser = givenMockUser();
        Receiver mockReceiver = givenMockReceiver();
        List<ProductOrder> mockProductOrders = givenMockProductOrders();
        Payment payment = Mockito.mock(Payment.class);

        return List.of(
                Order.builder()
                        .orderId(1L)
                        .orderNumber("202412161200000001")
                        .status(OrderStatus.ORDERED)
                        .summaryTitle("jacket")
                        .totalPrice(5000)
                        .request("none")
                        .user(mockUser)
                        .receiver(mockReceiver)
                        .productOrders(mockProductOrders)
                        .payment(payment)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .deletedAt(null)
                        .build(),
                Order.builder()
                        .orderId(2L)
                        .orderNumber("202412161200000002")
                        .status(OrderStatus.ORDERED)
                        .summaryTitle("coat")
                        .totalPrice(10000)
                        .request("test")
                        .user(mockUser)
                        .receiver(mockReceiver)
                        .productOrders(mockProductOrders)
                        .payment(payment)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .deletedAt(null)
                        .build(),
                Order.builder()
                        .orderId(3L)
                        .orderNumber("202412161200000003")
                        .status(OrderStatus.ORDERED)
                        .summaryTitle("padding")
                        .totalPrice(10000)
                        .request("message")
                        .user(mockUser)
                        .receiver(mockReceiver)
                        .productOrders(mockProductOrders)
                        .payment(payment)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .deletedAt(LocalDateTime.now())
                        .build()
        );
    }

    private Order givenOrder(boolean isCanceled, boolean canBeCanceled) {
        return Order.builder()
                .orderId(1L)
                .orderNumber("202412161200000001")
                .status(canBeCanceled ? OrderStatus.ORDERED : OrderStatus.SHIPPING)
                .summaryTitle("jacket")
                .totalPrice(10000)
                .request("test-only")
                .user(Mockito.mock(User.class))
                .receiver(Mockito.mock(Receiver.class))
                .productOrders(List.of(givenMockProductOrder()))
                .payment(Mockito.mock(Payment.class))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(isCanceled ? LocalDateTime.now() : null)
                .build();
    }
}