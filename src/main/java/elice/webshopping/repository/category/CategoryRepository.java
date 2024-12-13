package elice.webshopping.repository.category;

import elice.webshopping.domain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    //////
}
