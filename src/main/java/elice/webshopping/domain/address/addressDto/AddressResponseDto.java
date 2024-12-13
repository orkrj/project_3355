package elice.webshopping.domain.address.addressDto;

import elice.webshopping.domain.address.Address;

public record AddressResponseDto(String zipCode, String streetAddress, String detailAddress) {
    public AddressResponseDto(Address address) {
        this(address.getZipCode(), address.getStreetAddress(), address.getDetailAddress());
    }
}