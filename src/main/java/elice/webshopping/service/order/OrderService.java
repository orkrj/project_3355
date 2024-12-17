package elice.webshopping.service.order;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.order.OrderRequestDto;
import elice.webshopping.domain.order.OrderResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

    //== 주문 생성 ==//
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    //== 주문 전체 조회 ==//
    List<OrderResponseDto> getOrders();

    //== 주문 단건 조회 ==//
    OrderResponseDto getOrderResponseDtoById(Long orderId);

    //== 비즈니스 로직 내 사용할 단건 조회 ==//
    Order getOrderEntityById(Long orderId);

    //== 관리자 삭제를 위해 deletedAt != null 인 주문까지 조회 ==//
    Order getOrderEntityByIdIncludeDeletedAtIsNotNull(Long orderId);

    //== 주문 수정 ==// -> 배송 수정만 허용할 거라 배송 로직에서 수정 책임 있음

    //== 주문 취소: 유저 ==//
    void cancelOrder(Long orderId);

    //== 주문 삭제: 관리자 ==//
    void deleteOrder(Long orderId);
}
