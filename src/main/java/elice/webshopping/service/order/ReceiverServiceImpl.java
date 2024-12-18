package elice.webshopping.service.order;

import elice.webshopping.domain.order.Receiver;
import elice.webshopping.domain.order.ReceiverRequestDto;
import elice.webshopping.domain.order.ReceiverResponseDto;
import elice.webshopping.exception.order.ReceiverNotExistException;
import elice.webshopping.repository.order.ReceiverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiverServiceImpl implements ReceiverService {

    private final ReceiverRepository receiverRepository;

    @Override
    public ReceiverResponseDto createReceiver(ReceiverRequestDto receiverRequestDto) {
        Receiver savedReceiver = receiverRepository.save(Receiver.from(receiverRequestDto));
        return ReceiverResponseDto.from(savedReceiver);
    }

    @Override
    public ReceiverResponseDto findReceiverResponseDto(Long receiverId) {
        return ReceiverResponseDto.from(findReceiverEntityById(receiverId));
    }

    @Override
    public Receiver findReceiverEntityById(Long receiverId) {
        return receiverRepository.findById(receiverId)
                .orElseThrow(() -> new ReceiverNotExistException(receiverId));
    }

    @Override
    public ReceiverResponseDto updateReceiver(Long receiverId, ReceiverRequestDto receiverRequestDto) {
        Receiver findReceiver = findReceiverEntityById(receiverId);
        return ReceiverResponseDto.from(updateReceiverFields(receiverRequestDto, findReceiver));
    }

    private Receiver updateReceiverFields(ReceiverRequestDto receiverRequestDto, Receiver targetReceiver) {
        targetReceiver.setName(receiverRequestDto.name());
        targetReceiver.setPhoneNumber(receiverRequestDto.phoneNumber());
        targetReceiver.setZipCode(receiverRequestDto.zipCode());
        targetReceiver.setStreetAddress(receiverRequestDto.streetAddress());
        targetReceiver.setDetailAddress(receiverRequestDto.detailAddress());
        targetReceiver.setMessage(receiverRequestDto.message());

        return targetReceiver;
    }
}
