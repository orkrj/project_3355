package elice.webshopping.domain.address;

import elice.webshopping.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import elice.webshopping.domain.user.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "addresses")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Getter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(length = 255)
    private String zipCode;

    @Column(length = 255, name = "street_address")
    private String streetAddress;

    @Column(length = 255, name = "detail_address")
    private String detailAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Address(String zipCode, String streetAddress, String detailAddress) {
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
    }

    @Embedded
    private BaseEntity baseEntity;


}
