package elice.webshopping.service.order;

import elice.webshopping.domain.order.Receiver;
import elice.webshopping.domain.order.ReceiverRequestDto;
import elice.webshopping.domain.order.ReceiverResponseDto;
import elice.webshopping.repository.order.ReceiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiverServiceImpl implements ReceiverService {

    private final ReceiverRepository receiverRepository;

    @Override
    public ReceiverResponseDto createReceiver(ReceiverRequestDto receiverRequestDto) {
        return null;
    }

    @Override
    public ReceiverResponseDto findReceiverById(Long receiverId) {
        return null;
    }

    @Override
    public ReceiverResponseDto updateReceiver(Long receiverId, ReceiverRequestDto receiverRequestDto) {
        return null;
    }

}
