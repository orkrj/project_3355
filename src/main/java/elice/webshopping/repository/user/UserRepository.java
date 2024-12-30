package elice.webshopping.repository.user;

import elice.webshopping.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username); //id
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    void deleteByUsername(String username);

    void deleteByUserId(Long userId);
    //void deleteByUser_id(Long user_id);

}
