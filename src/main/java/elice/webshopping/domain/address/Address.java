package elice.webshopping.domain.address;

import elice.webshopping.domain.common.BaseEntity;
import elice.webshopping.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(length = 255, name = "zip_code")
    private String zipCode;

    @Column(length = 255, name = "street_address")
    private String streetAddress;

    @Column(length = 255, name = "detail_address")
    private String detailAddress;

    @Column(length = 50, name = "address_target")
    private String addressTarget;

    @Column(nullable = false, name = "is_base_address")
    private Boolean isBaseAddress;

    @Column(nullable = false, name = "is_deleted")
    private Boolean isDeleted = false;


    @Builder
    public Address(String zipCode, String streetAddress, String detailAddress,String addressTarget,
                   Boolean isBaseAddress, Boolean isDeleted, User user) {
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.addressTarget = addressTarget;
        this.isBaseAddress = isBaseAddress;
        this.isDeleted = isDeleted;
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public String getUsername() {
        return user.getUsername();
    }

//    public long getUserId() {
//        return user.getUser_id();
//    }

    public void update(String zipCode, String streetAddress, String detailAddress,
                       String addressTarget, Boolean isBaseAddress) {
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.detailAddress = detailAddress;
        this.addressTarget = addressTarget;
        this.isBaseAddress = isBaseAddress;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public void setBaseAddress(Boolean baseAddress) {
        isBaseAddress = baseAddress;
    }
}
