package elice.webshopping.repository.productOrder;

import elice.webshopping.domain.productOrder.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    @Query("SELECT po FROM ProductOrder po WHERE po.order.orderId = :orderId")
    List<Optional<ProductOrder>> findAllByOrderId(Long orderId);
}
