package elice.webshopping.repository.category;

import elice.webshopping.domain.category.Category;
import elice.webshopping.domain.product.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    //Category 이름으로 Product 찾기
    @Query("select p from Product p where p.category.name = :categoryName")
    List<Product> findProductBy(String categoryName);


    // 이름으로 Category 존재 여부 확인
    @Query("select count(*)>0 from Category c where c.name =:name")
    boolean existsByName(@Param("name") String name);

}
