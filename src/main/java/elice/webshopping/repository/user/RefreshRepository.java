package elice.webshopping.repository.user;

import elice.webshopping.domain.user.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {
    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);

}
