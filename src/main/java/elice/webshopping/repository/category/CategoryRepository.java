package elice.webshopping.repository.category;

import elice.webshopping.domain.category.Category;
import elice.webshopping.domain.product.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Long> {


    // 이름으로 Category 존재 여부 확인
    @Query("select count(*)>0 from Category c where c.name =:name")
    boolean existsByName(@Param("name") String name);

    Optional<Category> findByName(String name);

}
