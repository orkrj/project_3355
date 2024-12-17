package elice.webshopping.domain.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ReceiverRequestDto (
        @NotNull @NotEmpty String name,
        @NotNull @NotEmpty String phoneNumber,
        @NotNull @NotEmpty String zipCode,
        @NotNull @NotEmpty String streetAddress,
        @NotNull @NotEmpty String detailAddress,
        String message
) {}
