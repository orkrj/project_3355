package elice.webshopping.service.order;

import elice.webshopping.domain.order.Order;
import elice.webshopping.domain.order.OrderRequestDto;
import elice.webshopping.domain.order.OrderResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

    //== 주문 생성 ==//
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    //== 주문 전체 조회 ==//
    public List<OrderResponseDto> getOrders();

    //== 주문 단건 조회 ==//
    public OrderResponseDto getOrderResponseDtoById(Long orderId);

    //== 비즈니스 로직 내 사용할 단건 조회 ==//
    public Order getOrderEntityById(Long orderId);

    //== 주문 수정 ==//
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto orderRequestDto);

    //== 주문 취소: 유저 ==//
    public void cancelOrder(Long orderId);

    //== 주문 삭제: 관리자 ==//
    public void deleteOrder(Long orderId);
}
