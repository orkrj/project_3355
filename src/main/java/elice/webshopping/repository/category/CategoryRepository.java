package elice.webshopping.repository.category;

import elice.webshopping.domain.category.Category;
import elice.webshopping.domain.product.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select p from Product p where p.category.name = :categoryName")
    List<Product> findProductBy(String categoryName);

    //////
}
