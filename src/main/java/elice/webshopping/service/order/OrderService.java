package elice.webshopping.service.order;

import elice.webshopping.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderService extends JpaRepository<Order, Long> {
}
