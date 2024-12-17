package elice.webshopping.service.order;

import elice.webshopping.domain.order.Receiver;
import elice.webshopping.domain.order.ReceiverRequestDto;
import elice.webshopping.domain.order.ReceiverResponseDto;

public interface ReceiverService {

    //== 받는 사람 생성 ==//
    ReceiverResponseDto createReceiver(ReceiverRequestDto receiverRequestDto);

    //== 비즈니스 로직 내 사용할 받는 사람 조회 ==//
    ReceiverResponseDto findReceiverById(Long receiverId);

    //== 받는 사람 수정 ==//
    ReceiverResponseDto updateReceiver(Long receiverId, ReceiverRequestDto receiverRequestDto);
}
