package elice.webshopping.domain.order;

public record ReceiverResponseDto(
        String name,
        String phoneNumber,
        String zipCode,
        String streetAddress,
        String detailAddress
) {}
