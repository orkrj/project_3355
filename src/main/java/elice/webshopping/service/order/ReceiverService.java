package elice.webshopping.service.order;

import elice.webshopping.domain.order.Receiver;
import elice.webshopping.domain.order.ReceiverRequestDto;
import elice.webshopping.domain.order.ReceiverResponseDto;

public interface ReceiverService {

    //== 받는 사람 dto 생성 ==//
    ReceiverResponseDto createReceiverResponse(ReceiverRequestDto receiverRequestDto);

    //== 받는 사람 엔티티 생성 ==//
    Receiver createReceiverEntity(ReceiverRequestDto receiverRequestDto);

    //== 받는 사람 조회 ==//
    ReceiverResponseDto findReceiverResponseDto(Long receiverId);

    //== 비즈니스 로직 내 사용할 받는 사람 조회 ==//
    Receiver findReceiverEntityById(Long receiverId);

    //== 받는 사람 수정 ==//
    ReceiverResponseDto updateReceiver(Long receiverId, ReceiverRequestDto receiverRequestDto);
}
