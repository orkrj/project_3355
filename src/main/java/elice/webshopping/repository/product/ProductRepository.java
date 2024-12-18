package elice.webshopping.repository.product;

import elice.webshopping.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // 삭제되지 않은 상품만 조회 (deletedAt이 null인 상품만)
    List<Product> findByDeletedAtIsNull();

    // 상품 ID와 deletedAt이 null인 상품 조회
    Optional<Product> findByProductIdAndDeletedAtIsNull(Long productId);

    // Soft delete: deletedAt 값을 현재 시간으로 업데이트
    @Modifying
    @Query("UPDATE Product p SET p.deletedAt = :deletedAt WHERE p.productId = :productId AND p.deletedAt IS NULL")
    int softDeleteProduct(@Param("productId") Long productId, @Param("deletedAt") LocalDateTime deletedAt);
}
