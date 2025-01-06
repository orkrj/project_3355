package elice.webshopping.service.order;

import elice.webshopping.domain.order.*;
import elice.webshopping.domain.user.User;

import java.util.List;

public interface OrderService {

    //== 주문 생성 ==//
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto, User user);

    //== 주문 전체 조회 ==//
    List<OrderResponseDto> getOrders();

    //== 유저별 주문 조회 ==//
    List<OrderResponseDto> getOrdersByUser(User user);

    //== 주문 단건 조회 ==//
    OrderResponseDto getOrderResponseDtoById(Long orderId);

    //== 비즈니스 로직 내 사용할 단건 조회 ==//
    Order getOrderEntityById(Long orderId);

    //== 주문 상태 조회 ==//
    OrderStatus getOrderStatus(Long orderId);

    //== 관리자 삭제를 위해 deletedAt != null 인 주문까지 조회 ==//
    Order getOrderEntityByIdIncludeDeletedAtIsNotNull(Long orderId);

    //== 결제와 연동하기 위해 orderNumber 로 order entity 조회 ==//
    Order getOrderEntityByOrderNumber(String orderNumber);

    //== 주문 상태 수정 ==//
    OrderStatus updateOrderStatus(OrderStatusUpdateRequestDto orderStatusUpdateRequestDto);

    //== 주문 취소: 유저 ==//
    void cancelOrder(Long orderId);

    //== 주문 삭제: 관리자 ==//
    void deleteOrderById(Long orderId);

    //== 주문 전체 삭제: 관리자 ==//
    void deleteOrders(User user);
}
