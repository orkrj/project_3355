package elice.webshopping.domain.order;

public record ReceiverRequestDto (
        String name,
        String phoneNumber,
        String zipCode,
        String streetAddress,
        String detailAddress
) {}
