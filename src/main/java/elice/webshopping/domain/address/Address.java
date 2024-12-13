package elice.webshopping.domain.address;

import elice.webshopping.domain.common.BaseEntity;
import elice.webshopping.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Builder
    public Address(String zipCode, String streetAddress, String detailAddress) {
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Embedded
    private BaseEntity baseEntity;


}
