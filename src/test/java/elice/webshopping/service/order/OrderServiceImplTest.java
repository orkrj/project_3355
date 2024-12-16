package elice.webshopping.service.order;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.order.OrderResponseDto;
import elice.webshopping.domain.order.OrderStatus;
import elice.webshopping.domain.order.Receiver;
import elice.webshopping.domain.product.Product;
import elice.webshopping.domain.productOrder.ProductOrder;
import elice.webshopping.domain.user.User;
import elice.webshopping.repository.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("주문 전체 조회: 삭제되지 않은 주문만")
    void shouldReturnAllNonDeletedOrders() {

        // given
        List<Order> mockOrders = givenMockOrders();
        given(orderRepository.findAll()).willReturn(mockOrders);

        // when
        List<OrderResponseDto> orders = orderService.getOrders();

        // then
        assertEquals(2, orders.size(), "소프트 딜리트된 test 3 은 조회되면 안 됨");
        assertEquals("202412161200000001", orders.get(0).orderNumber());
        assertEquals("CASH", orders.get(1).payment());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("주문 단건 조회: 삭제되지 않은 경우")
    void shouldReturnNonDeletedOrderEntityById() {

        // given
        Order mockOrder = givenMockOrder(false);
        given(orderRepository.findById(1L)).willReturn(Optional.of(mockOrder));

        // when
        Order order = orderService.getOrderEntityById(1L);

        // then
        assertEquals(1, order.getOrderId());
        assertEquals("202412161200000001", order.getOrderNumber());
        assertEquals("CARD", order.getPayment());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("주문 단건 조회: 삭제된 경우 예외 발생")
    void shouldThrowException_whenGetDeletedOrderEntityById() {

        // given
        Order mockOrder = givenMockOrder(true);
        given(orderRepository.findById(1L)).willReturn(Optional.of(mockOrder));

        // when, then
        assertThrows(NotFoundException.class, () -> orderService.getOrderEntityById(1L));
        verify(orderRepository, times(1)).findById(1L);
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

    private ProductOrder givenMockProductOrder() {
        ProductOrder mockProductOrder = Mockito.mock(ProductOrder.class);
        Product mockProduct = Mockito.mock(Product.class);

        Mockito.when(mockProduct.getProductId()).thenReturn(1L);
        Mockito.when(mockProductOrder.getProduct()).thenReturn(mockProduct);
        Mockito.when(mockProductOrder.getQuantity()).thenReturn(1);

        return mockProductOrder;
    }

    private List<Order> givenMockOrders() {
        User mockUser = givenMockUser();
        Receiver mockReceiver = givenMockReceiver();
        ProductOrder mockProductOrder = givenMockProductOrder();

        return List.of(
                Order.builder()
                        .orderId(1L)
                        .orderNumber("202412161200000001")
                        .payment("CARD")
                        .message("getOrders 1")
                        .status(OrderStatus.ORDERED)
                        .totalPrice(5000)
                        .user(mockUser)
                        .receiver(mockReceiver)
                        .productOrders(List.of(mockProductOrder))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .deletedAt(null)
                        .build(),
                Order.builder()
                        .orderId(2L)
                        .orderNumber("202412161200000002")
                        .payment("CASH")
                        .message("getOrders 2")
                        .status(OrderStatus.ORDERED)
                        .totalPrice(10000)
                        .user(mockUser)
                        .receiver(mockReceiver)
                        .productOrders(List.of(mockProductOrder))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .deletedAt(null)
                        .build(),
                Order.builder()
                        .orderId(3L)
                        .orderNumber("202412161200000003")
                        .payment("CASH")
                        .message("getOrders 3")
                        .status(OrderStatus.ORDERED)
                        .totalPrice(10000)
                        .user(mockUser)
                        .receiver(mockReceiver)
                        .productOrders(List.of(mockProductOrder))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .deletedAt(LocalDateTime.now())
                        .build()
        );
    }

    private Order givenMockOrder(boolean isDeleted) {
        return Order.builder()
                .orderId(1L)
                .orderNumber("202412161200000001")
                .payment("CARD")
                .message("getOrderEntityById")
                .status(OrderStatus.ORDERED)
                .totalPrice(10000)
                .user(Mockito.mock(User.class))
                .receiver(Mockito.mock(Receiver.class))
                .productOrders(List.of(Mockito.mock(ProductOrder.class)))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .deletedAt(isDeleted ? LocalDateTime.now() : null)
                .build();
    }
}