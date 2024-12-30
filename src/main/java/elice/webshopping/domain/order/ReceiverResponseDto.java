package elice.webshopping.domain.order;

public record ReceiverResponseDto(
        String name,
        String phoneNumber,
        String zipCode,
        String streetAddress,
        String detailAddress
) {

    public static ReceiverResponseDto from(Receiver receiver) {
        return new ReceiverResponseDto(
                receiver.getName(),
                receiver.getPhoneNumber(),
                receiver.getZipCode(),
                receiver.getStreetAddress(),
                receiver.getDetailAddress()
        );
    }

    public static ReceiverResponseDto from(Order order) {
        return new ReceiverResponseDto(
                order.getReceiver().getName(),
                order.getReceiver().getPhoneNumber(),
                order.getReceiver().getZipCode(),
                order.getReceiver().getStreetAddress(),
                order.getReceiver().getDetailAddress()
        );
    }
}
