package elice.webshopping.service.order;

import elice.webshopping.domain.order.Receiver;
import elice.webshopping.domain.order.ReceiverRequestDto;

public interface ReceiverService {

    //== 받는 사람 생성 ==//
    public Receiver createReceiver(ReceiverRequestDto receiverRequestDto);

    //== 비즈니스 로직 내 사용할 받는 사람 조회 ==//
    public Receiver findReceiverById(Long receiverId);

    //== 받는 사람 수정 ==//
    public Receiver updateReceiver(Long receiverId, ReceiverRequestDto receiverRequestDto);
}
