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

    public ReceiverResponseDto createReceiverResponse(ReceiverRequestDto receiverRequestDto) {
        Receiver savedReceiver = receiverRepository.save(Receiver.from(receiverRequestDto));
        return ReceiverResponseDto.from(savedReceiver);
    }

    @Override
    public Receiver createReceiverEntity(ReceiverRequestDto receiverRequestDto) {

        return receiverRepository.save(Receiver.from(receiverRequestDto));
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
        targetReceiver.setName(receiverRequestDto.receiverName());
        targetReceiver.setPhoneNumber(receiverRequestDto.receiverPhoneNumber());
        targetReceiver.setZipCode(receiverRequestDto.postalCode());
        targetReceiver.setStreetAddress(receiverRequestDto.address1());
        targetReceiver.setDetailAddress(receiverRequestDto.address2());

        return targetReceiver;
    }
}
