package elice.webshopping.service.address;

import elice.webshopping.domain.address.addressDto.*;
import elice.webshopping.domain.address.Address;

import elice.webshopping.domain.user.User;
import elice.webshopping.exception.order.address.AddressNotFoundException;
import elice.webshopping.repository.address.AddressRepository;
import elice.webshopping.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public void save(AddressRequestDto request) {
        // username으로 User 조회
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + request.getUsername()));

        //기본 배송지를 처리하는 로직
        if (request.getIsBaseAddress() && !addressRepository.findAllByUsername(user.getUsername()).isEmpty()) {
            List<Address> existingAddresses = addressRepository.findAllByUsername(request.getUsername());

            for (Address address : existingAddresses) {
                if (address.getIsBaseAddress()) {
                    address.setBaseAddress(false); // 기존 기본 배송지 해제
                    addressRepository.save(address);
                }
            }
        } else if (!request.getIsBaseAddress() ) {
            request.setBaseAddress(true); // 첫 번째 주소는 기본 배송지로 설정
        }

        // 동일한 addressTarget이 존재하면 예외 처리
        if (addressRepository.findByAddressTarget(request.getUsername(), request.getAddressTarget()).isPresent() ) {
            throw new IllegalArgumentException("동잏한 배송지 명인 " + request.getAddressTarget() + "가 존재합니다.");
        }

        // Address 엔티티 생성 후 저장
        addressRepository.save(request.toEntity(user));
    }

    //관리자용 전체 아이디들이 가지고 있는 모든 주소를 조회하기 위한 용도
    public List<AddressResponseDto> findAll() {
        return addressRepository.findAll().stream()
                .filter(address -> Boolean.FALSE.equals(address.getIsDeleted())) // isBaseAddress가 false인 경우만 필터링
                .map(AddressResponseDto::new) // Address -> AddressResponseDto 변환
                .toList();
    }

    //유저 개인을 위한 용도, 유저가 가지고 있는 모든 주소를 조회
    public List<AddressResponseDto> findAddress(String username) {
        List<Address> addresses = addressRepository.findAllByUsername(username);

        if (addresses.isEmpty()) {
            throw new AddressNotFoundException("해당 아이디에는 주소가 존재하지 않습니다.");
        }

        // 모든 Address 객체를 AddressResponseDto로 변환하여 반환
        return addresses.stream()
                .map(AddressResponseDto::new) // Address -> AddressResponseDto 변환
                .toList();
    }

    //주문을 위한 용도, 유저가 본인이 가지고 있는 여러 배송주소 중에서 등록한 기본 배송 주소를 불러온다.
    public AddressResponseDto findBaseAddresses(String username) {
        Address basicAddress = addressRepository.findBaseAddress(username)
                            .orElseThrow(() -> new AddressNotFoundException("해당 주소를 찾을 수 없습니다."));


        return new AddressResponseDto(basicAddress);
    }

    public AddressResponseDto update(AddressRequestDto request) {
        // username과 addressTarget으로 Address 조회
        Address address = addressRepository.findByAddressTarget(request.getUsername(), request.getAddressTarget())
                .orElseThrow(() -> new AddressNotFoundException("해당 주소를 찾을 수 없습니다."));

        // Address 엔티티 업데이트
        address.update(request.getZipCode(), request.getStreetAddress(), request.getDetailAddress(),
                request.getAddressTarget(), request.getIsBaseAddress());
        Address updatedAddress = addressRepository.save(address);

        return new AddressResponseDto(updatedAddress);
    }

    public void delete(String username, String addressTarget) {
        Address basicAddress = addressRepository.findByAddressTarget(username, addressTarget)
                .orElseThrow(() -> new AddressNotFoundException("해당 주소를 찾을 수 없습니다."));

        basicAddress.setDeleted(true);

    }
}