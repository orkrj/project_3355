package elice.webshopping.controller.order;

import elice.webshopping.domain.order.ReceiverRequestDto;
import elice.webshopping.domain.order.ReceiverResponseDto;
import elice.webshopping.service.order.ReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receiver")
@RequiredArgsConstructor
public class ReceiverController {

    private final ReceiverService receiverService;

    @PostMapping
    public ResponseEntity<ReceiverResponseDto> createOrder(@RequestBody ReceiverRequestDto receiverRequestDto) {
        return ResponseEntity.ok(receiverService.createReceiverResponse(receiverRequestDto));
    }

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
