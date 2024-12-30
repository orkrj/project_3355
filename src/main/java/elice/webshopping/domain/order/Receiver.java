package elice.webshopping.domain.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "receivers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Receiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long receiverId;

    private String name;

    private String phoneNumber;

    private String zipCode;

    private String streetAddress;

    private String detailAddress;

    // 주문에서 받는 사람을 불러오는 것만 필요하니 단방향으로 설계
    // 받는 사람으로 주문 조회 불가

    public static Receiver from(ReceiverRequestDto receiverRequestDto) {
        Receiver receiver = new Receiver();
        receiver.setName(receiverRequestDto.receiverName());
        receiver.setPhoneNumber(receiverRequestDto.receiverPhoneNumber());
        receiver.setZipCode(receiverRequestDto.postalCode());
        receiver.setStreetAddress(receiverRequestDto.address1());
        receiver.setDetailAddress(receiverRequestDto.address2());

        return receiver;
    }
}
