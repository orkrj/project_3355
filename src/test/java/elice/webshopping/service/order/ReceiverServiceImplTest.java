package elice.webshopping.service.order;

import elice.webshopping.domain.order.Receiver;
import elice.webshopping.domain.order.ReceiverRequestDto;
import elice.webshopping.repository.order.ReceiverRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiverServiceImplTest {

    @Mock
    private ReceiverRepository receiverRepository;

    @InjectMocks
    private ReceiverServiceImpl receiverService;

    @Test
    @DisplayName("배송 정보 저장")
    void createReceiverEntity() {

        // given
        Receiver receiver = givenReceiver();
        given(receiverRepository.save(any())).willReturn(receiver);

        // when
        Receiver receiverResponseDto = receiverService.createReceiverEntity(givenRequestReceiverDto());

        // then
        assertEquals("test", receiver.getName());
        assertEquals("01000000000", receiver.getPhoneNumber());
        assertEquals("12345", receiver.getZipCode());
        assertEquals("Seonggyungwan-ro", receiver.getStreetAddress());
        assertEquals("25-2", receiver.getDetailAddress());
        verify(receiverRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("배송 정보 조회")
    void findReceiverById() {

        // given
        Receiver receiver = givenReceiver();
        given(receiverRepository.findById(1L)).willReturn(Optional.of(receiver));

        // when
        Receiver findReceiver = receiverService.findReceiverEntityById(1L);

        // then
        assertEquals("test", findReceiver.getName());
        assertEquals("01000000000", findReceiver.getPhoneNumber());
        assertEquals("12345", findReceiver.getZipCode());
        assertEquals("Seonggyungwan-ro", findReceiver.getStreetAddress());
        assertEquals("25-2", findReceiver.getDetailAddress());
        verify(receiverRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("배송 정보 수정")
    void updateReceiver() {

        // given
        Receiver receiver = givenReceiver();
        ReceiverRequestDto receiverRequestDtoForUpdate = givenReceiverRequestDtoForUpdate();
        given(receiverRepository.findById(1L)).willAnswer(result -> {
            if (receiver.getName().equals("test")) {
                return Optional.of(receiver);
            } else {
                return Optional.of(receiver);
            }
        });

        // when
        receiverService.updateReceiver(1L, receiverRequestDtoForUpdate);

        // then
        assertEquals("update", receiver.getName());
        assertEquals("01012345678", receiver.getPhoneNumber());
        assertEquals("54321", receiver.getZipCode());
        assertEquals("Seonggyungwan-ro", receiver.getStreetAddress());
        assertEquals("25-2", receiver.getDetailAddress());
        verify(receiverRepository, times(1)).findById(1L);
    }

    private ReceiverRequestDto givenRequestReceiverDto() {
        return new ReceiverRequestDto (
                "test",
                "01000000000",
                "12345",
                "Seonggyungwan-ro",
                "25-2"
        );
    }

    private Receiver givenReceiver() {
        return Receiver.from(givenRequestReceiverDto());
    }

    private ReceiverRequestDto givenReceiverRequestDtoForUpdate() {
        return new ReceiverRequestDto (
                "update",
                "01012345678",
                "54321",
                "Seonggyungwan-ro",
                "25-2"
        );
    }
}