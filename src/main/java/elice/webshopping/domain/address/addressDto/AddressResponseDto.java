package elice.webshopping.domain.address.addressDto;

import elice.webshopping.domain.address.Address;

public record AddressResponseDto(
        String zipCode, String streetAddress, String detailAddress,
        String addressTarget, Boolean isBaseAddress, Boolean isDeleted,
        String username
) {
    public AddressResponseDto(Address address) {
        this(
                address.getZipCode(),
                address.getStreetAddress(),
                address.getDetailAddress(),
                address.getAddressTarget(),
                address.getIsBaseAddress(),
                address.getIsDeleted(),
                address.getUsername()
        );
    }
}