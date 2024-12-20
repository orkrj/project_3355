package elice.webshopping.controller.order;

import elice.webshopping.domain.order.ReceiverRequestDto;
import elice.webshopping.domain.order.ReceiverResponseDto;
import elice.webshopping.service.order.ReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receiver")
@RequiredArgsConstructor
public class ReceiverController {

    private final ReceiverService receiverService;

//    // TODO 생성 로직 미구현: UserService 필요
//    @PostMapping
//    public ResponseEntity<ReceiverResponseDto> createOrder(@RequestBody ReceiverRequestDto receiverRequestDto) {
//        return ResponseEntity.ok(receiverService.createReceiver(receiverRequestDto));
//    }

    @GetMapping("/{receiverId}")
    public ResponseEntity<ReceiverResponseDto> findReceiverById(@PathVariable Long receiverId) {
        return ResponseEntity.ok(receiverService.findReceiverResponseDto(receiverId));
    }

    @PutMapping("/{receiverId}")
    public ResponseEntity<ReceiverResponseDto> updateReceiver(
            @PathVariable Long receiverId,
            @Validated @RequestBody ReceiverRequestDto receiverRequestDto
    ) {
        return ResponseEntity.ok(receiverService.updateReceiver(receiverId, receiverRequestDto));
    }
}
