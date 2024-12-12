package elice.webshopping.domain;

import elice.webshopping.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "addresses")
@Getter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long address_id;

    private String zipCode; //우편번호

    private String street_address; //도로명주소

    private String detail_address; //상세주소

    /*
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    */
}
