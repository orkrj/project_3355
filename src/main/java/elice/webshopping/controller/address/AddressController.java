package elice.webshopping.controller.address;

import elice.webshopping.domain.address.addressDto.AddressRequestDto;
import elice.webshopping.domain.address.addressDto.AddressResponseDto;
import elice.webshopping.service.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/address/form")
    public ResponseEntity<AddressResponseDto> addAddress(@RequestBody AddressRequestDto request) {
        AddressResponseDto savedAddress = addressService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedAddress);
    }

    @GetMapping("/address")
    public ResponseEntity<List<AddressResponseDto>> findAllAddress() {
        List<AddressResponseDto> addresses = addressService.findAll();
        return ResponseEntity.ok()
                .body(addresses);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<AddressResponseDto> findAddress(@PathVariable(name = "id") long id) {
        AddressResponseDto address = addressService.findById(id);
        return ResponseEntity.ok()
                .body(address);
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<AddressResponseDto> updateAddress(@PathVariable(name = "id") long id,
                                                           @RequestBody AddressRequestDto request) {
        AddressResponseDto address = addressService.update(id, request);

        return ResponseEntity.ok()
                .body(address);
    }

}
