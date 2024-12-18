package elice.webshopping.repository.address;

import elice.webshopping.domain.address.Address;
import elice.webshopping.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE a.user.username = :username")
    List<Address> findAllByUsername(@Param("username") String username);

    boolean existsByUserAndAddressTarget(User user, String addressTarget);

    @Query("SELECT a FROM Address a WHERE a.isDeleted = false AND a.user.username = :username AND a.addressTarget =: addressTarget")
    Optional<Address> findByUsernameAndAddressTarget(@Param("username") String username);

    //기본 주소로 지정되어 있으면서, 삭제되지 않은 주소를 가져오기
    @Query("SELECT a FROM Address a WHERE a.isDeleted = false AND a.user.username = :username AND a.isBaseAddress = true")
    Optional<Address> findByUsernameAndBaseAddress(@Param("username") String username);

    //테스트용, 기본 배송지가 false로 바꼈나 테스트
    @Query("SELECT a FROM Address a  WHERE a.isDeleted = false AND a.user.user_id =: userId ")
    List<Address> findByAddAddresses();

    //해당 조건에 만족하는 레코드가 없을 경우 아무 동작도 하지 않고 쿼리만 종료
    @Modifying
    @Query("UPDATE Address a SET a.isBaseAddress = false WHERE a.user.user_id = :userId AND a.isBaseAddress = true")
    void resetBaseAddresses(@Param("userId") long userId);


    // 기본적으로 삭제되지 않은 데이터만 조회
    @Query("SELECT a FROM Address a WHERE a.isDeleted = false AND a.user.username = :username")
    List<Address> findAllActiveByUsername(@Param("username") String username);

    // 관리자용 메서드: 모든 데이터 조회 (isDeleted 관계없이)
    @Query("SELECT a FROM Address a WHERE a.user.username = :username")
    List<Address> findAllIncludingDeletedByUsername(@Param("username") String username);
}
