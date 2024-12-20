package elice.webshopping.repository.address;

import elice.webshopping.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    //현재 username에 해당하는 모든 필드를 불러옴
    @Query("SELECT a FROM Address a WHERE a.isDeleted = false AND a.user.username = :username")
    List<Address> findAllByUsername(@Param("username") String username);


    //기존에 유저ID에서 기본 주소로 지정되어 있으면서, 삭제되지 않은 주소를 가져오기
    @Query("SELECT a FROM Address a  WHERE a.isDeleted = false AND a.isBaseAddress = true And a.user.username = :username")
    Optional<Address> findBaseAddress(@Param("username") String username);


    // 동일한 addressTarget이 존재하면 예외 처리
    @Query("SELECT a FROM Address a WHERE a.isDeleted = false AND a.user.username = :username AND a.addressTarget = :addressTarget")
    Optional<Address> findByAddressTarget(@Param("username") String username, @Param("addressTarget") String addressTarget);


    // 관리자용 메서드: 모든 데이터 조회 (isDeleted 관계없이) 그냥 findAll 쓰면 될 것 같다
}

//user id로 찾을 경우 대비
//    @Query("SELECT a FROM Address a  WHERE a.isDeleted = false AND a.isBaseAddress = true And a.user.user_id =: userId")
//    Optional<Address> findBaseAddresses(@Param("userId") long userId);