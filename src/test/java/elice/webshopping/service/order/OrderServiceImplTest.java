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

import java.time.LocalDateTime;
import java.util.List;

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
    @DisplayName("주문 전체 조회")
    void getOrdersReturnNonDeletedOrders_WhenGetOrdersIsCalled() {

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
                        .message("Test 1")
                        .status(OrderStatus.ORDER_COMPLETED)
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
                        .message("Test 2")
                        .status(OrderStatus.ORDER_COMPLETED)
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
                        .message("Test 3")
                        .status(OrderStatus.ORDER_COMPLETED)
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
}