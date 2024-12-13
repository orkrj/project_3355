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

    public Address toEntity() {
        return Address.builder()
                .zipCode(zipCode)
                .streetAddress(streetAddress)
                .detailAddress(detailAddress)
                .build();
    }
}
