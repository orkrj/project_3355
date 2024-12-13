package elice.webshopping.service.address;

import elice.webshopping.domain.address.addressDto.*;
import elice.webshopping.domain.address.Address;
import elice.webshopping.repository.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressResponseDto save(AddressRequestDto request) {
        Address savedAddress = addressRepository.save(request.toEntity());

        return new AddressResponseDto(savedAddress);
    }

    public List<AddressResponseDto> findAll() {
        return addressRepository.findAll().stream()
                .map(AddressResponseDto::new)
                .toList();
    }

    public AddressResponseDto findById(long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found: " + id));

        return new AddressResponseDto(address);
    }



    public AddressResponseDto update(long id, AddressRequestDto request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found: " + id));

        address.update(request.getZipCode(), request.getStreetAddress(), request.getDetailAddress());

        Address updatedAddress = addressRepository.save(address);

        return new AddressResponseDto(updatedAddress);
    }
}
