package elice.webshopping.domain.address.addressDto;

import elice.webshopping.domain.address.Address;
import elice.webshopping.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddressRequestDto {
    private String zipCode;
    private String streetAddress;
    private String detailAddress;
    private String addressTarget;
    private Boolean isBaseAddress;
    private Boolean isDeleted;
    private String username;


    public Address toEntity(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");

        return Address.builder()
                .zipCode(zipCode)
                .streetAddress(streetAddress)
                .detailAddress(detailAddress)
                .addressTarget(addressTarget)
                .isBaseAddress(isBaseAddress)
                .isDeleted(isDeleted)
                .user(user)
                .build();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAddressTarget(String addressTarget) {
        this.addressTarget = addressTarget;
    }

    public void setBaseAddress(Boolean baseAddress) {
        isBaseAddress = baseAddress;
    }
}
