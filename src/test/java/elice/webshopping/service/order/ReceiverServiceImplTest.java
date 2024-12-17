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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        ReceiverRequestDto receiverRequestDto = givenRequestReceiverDto();
        Receiver receiver = Receiver.from(receiverRequestDto);
        when(receiverRepository.save(any())).thenReturn(receiver);

        // when
        ReceiverResponseDto receiverResponseDto = receiverService.createReceiver(receiverRequestDto);

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

        // when

        // then
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
}