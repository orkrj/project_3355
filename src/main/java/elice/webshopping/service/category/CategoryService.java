package elice.webshopping.service.category;

import elice.webshopping.domain.category.Category;
import elice.webshopping.domain.product.Product;
import elice.webshopping.repository.category.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    //자식을 저장하려면 => 이름, 부모ID
    //부모를 저장하려면 => 이름, 부모ID
    public Category save(String name, Long parentId){

        Category category = Category.from(name);

        //자식 카테고리인 경우 부모설정
        if (parentId != null) {

            Category parent = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 ID가 없습니다."));

            parent.addChild(category);
        }
        return categoryRepository.save(category);
    }

    public void update(String name, Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID가 없습니다."));

        category.update(name);
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }

    public List<Product> findProductBy(String categoryName) {
        return categoryRepository.findProductBy(categoryName);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
