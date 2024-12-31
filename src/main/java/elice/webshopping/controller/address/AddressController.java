package elice.webshopping.controller.address;

import elice.webshopping.domain.address.addressDto.AddressRequestDto;
import elice.webshopping.domain.address.addressDto.AddressResponseDto;
import elice.webshopping.service.address.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/addressform")
    public ResponseEntity<String> addAddress(
            @Valid @RequestBody AddressRequestDto request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) { // 유효성 검사에서 에러가 발생했을 경우
            return ResponseEntity.badRequest().body("Invalid input data");
        }

        addressService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/addressall")
    public ResponseEntity<List<AddressResponseDto>> findAllAddress() {
        List<AddressResponseDto> addresses = addressService.findAll();
        return ResponseEntity.ok()
                .body(addresses);
    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressResponseDto>> findAddress(@RequestParam("username") String username) {
        List<AddressResponseDto> address = addressService.findAddress(username);
        return ResponseEntity.ok()
                .body(address);
    }

    @GetMapping("/particularaddress")
    public ResponseEntity<AddressResponseDto> findBaseAddress(@RequestParam("username") String username) {
        AddressResponseDto baseAddresses = addressService.findBaseAddresses(username);
        return ResponseEntity.ok().body(baseAddresses);
    }

    @PutMapping("/particularaddress")
    public ResponseEntity<?> updateBaseAddress(
            @Valid @RequestBody AddressRequestDto requestDto, // @RequestBody 하나만 사용
            BindingResult bindingResult // 유효성 검사 결과를 받을 BindingResult
    ) {
        if (bindingResult.hasErrors()) { // 유효성 검사에서 에러가 발생했을 경우
            return ResponseEntity.badRequest().body("Invalid input data");
        }

        addressService.update(requestDto); // 서비스 호출

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/particularaddress")
    public ResponseEntity<Void> deleteBaseAddress(@RequestParam("username") String username,
                                                  @RequestParam("addaddressTarget") String addressTarget) {
        addressService.delete(username, addressTarget);
        return ResponseEntity.ok()
                .build();
    }

}
