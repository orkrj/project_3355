package elice.webshopping.service.order;

import elice.webshopping.domain.order.Receiver;
import elice.webshopping.domain.order.ReceiverRequestDto;
import elice.webshopping.domain.order.ReceiverResponseDto;
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
    void createReceiver() {

        // given
        Receiver receiver = givenReceiver();
        given(receiverRepository.save(any())).willReturn(receiver);

        // when
        ReceiverResponseDto receiverResponseDto = receiverService.createReceiver(givenRequestReceiverDto());

        // then
        assertEquals("test", receiverResponseDto.name());
        assertEquals("01000000000", receiverResponseDto.phoneNumber());
        assertEquals("12345", receiverResponseDto.zipCode());
        assertEquals("Seonggyungwan-ro", receiverResponseDto.streetAddress());
        assertEquals("25-2", receiverResponseDto.detailAddress());
        verify(receiverRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("배송 정보 조회")
    void findReceiverById() {

        // given
        Receiver receiver = givenReceiver();
        receiver.setReceiverId(1L);
        given(receiverRepository.findById(receiver.getReceiverId())).willReturn(Optional.of(receiver));

        // when
        Receiver findReceiver = receiverService.findReceiverById(1L);

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

        // when

        // then
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
}