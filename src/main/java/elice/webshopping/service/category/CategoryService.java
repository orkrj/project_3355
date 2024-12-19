package elice.webshopping.service.category;

import elice.webshopping.domain.category.Category;
import elice.webshopping.domain.category.CategoryDto;
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
    public CategoryDto save(String name, Long parentId){

        //이미 이름이 있다면 중복예외 발생시키기
        if(categoryRepository.existsByName(name)){
            throw new IllegalArgumentException(name+"은 이미 존재하는 카테고리 이름입니다");
        }

        Category category = Category.from(name);

        //자식 카테고리인 경우 부모설정
        if (parentId != null) {

            Category parent = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new IllegalArgumentException("부모 ID가 없습니다."));

            // 루트카테고리에서 카테고리를 추가하지 않는경우 오류 발생
            if (parent.isNotRootCategory()) {
                throw new IllegalArgumentException("카테고리 추가는 루트카테고리만 할 수 있습니다.");
            }

            parent.addChild(category);
        }

        return categoryRepository.save(category).toDto();
    }


    public CategoryDto update(String name, Long id){

        //이미 이름이 있다면 중복예외 발생시키기
        if(categoryRepository.existsByName(name)){
            throw new IllegalArgumentException(name+"은 이미 존재하는 카테고리 이름입니다");
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID가 없습니다."));

        category.update(name);
        return categoryRepository.save(category).toDto();
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> findProductBy(String categoryName) {
        return categoryRepository.findProductBy(categoryName);
    }

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {

        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categories.stream()
                                        .map(Category::toDto).toList();
        return categoryDtos;
    }
}
