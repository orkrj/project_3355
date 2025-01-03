package elice.webshopping.service.category;

import elice.webshopping.domain.category.Category;
import elice.webshopping.domain.category.CategoryDto;
import elice.webshopping.domain.product.Product;
import elice.webshopping.repository.category.CategoryRepository;
import elice.webshopping.repository.product.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {

        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categories.stream()
                .map(Category::toDto).toList();
        return categoryDtos;
    }

    public CategoryDto save(String name, Long parentId){

        // 이스케이프 처리
        String escapedName = StringEscapeUtils.escapeHtml4(name);

        validate(escapedName);

        Category category = Category.from(escapedName);

        // 자식 카테고리인 경우 부모 설정
        if (parentId != null) {

            Category parent = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 ID가 없습니다."));

            // 루트 카테고리가 아닌 곳에서 카테고리를 추가하는 경우 오류 발생
            if (parent.isNotRootCategory()) {
                throw new IllegalArgumentException("카테고리 추가는 루트 카테고리만 할 수 있습니다.");
            }

            parent.addChild(category);
        }

        return categoryRepository.save(category).toDto();
    }

    public CategoryDto update(String name, Long id){

        // 이스케이프 처리
        String escapedName = StringEscapeUtils.escapeHtml4(name);

        validate(escapedName);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID가 없습니다."));

        category.update(escapedName);
        return categoryRepository.save(category).toDto();
    }

    private void validate(String escapedName) {
        if (escapedName == null || escapedName.trim().isEmpty()) {
            throw new IllegalArgumentException("카테고리 이름은 공백일 수 없습니다.");
        }

        // 이미 이름이 있다면 중복예외 발생
        if(categoryRepository.existsByName(escapedName)){
            throw new IllegalArgumentException(escapedName + "은 이미 존재하는 카테고리 이름입니다");
        }
    }

    public void delete(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID가 없습니다."));

        // "미분류" 카테고리 조회
        Category unclassifiedCategory = categoryRepository.findByName("미분류")
                .orElseThrow(() -> new IllegalArgumentException("\"미분류\" 카테고리가 없습니다."));

        // 카테고리 삭제 시 해당 상품 카테고리를 "미분류"로 변경
        String categoryName = category.getName();
        List<Product> products = productRepository.findProductBy(categoryName);
        for (Product product : products) {
            product.setCategory(unclassifiedCategory);
            productRepository.save(product);
        }

        categoryRepository.deleteById(id);
    }
}
