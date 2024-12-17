package elice.webshopping.repository.order;

import elice.webshopping.domain.order.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverRepository extends JpaRepository<Receiver, Long> {
}
