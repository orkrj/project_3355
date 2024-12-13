package elice.webshopping.service.category;

import elice.webshopping.domain.category.Category;
import elice.webshopping.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;


    @Override
    public void run(String... args) throws Exception {
        // 최상위 카테고리와 하위 카테고리 매핑
        List<CategoryData> categories = Arrays.asList(
                new CategoryData("Outer", Arrays.asList("코트", "자켓", "가디건")),
                new CategoryData("Top", Arrays.asList("티셔츠", "맨투맨")),
                new CategoryData("Bottom", Arrays.asList("데님", "슬랙스")),
                new CategoryData("ACC", Arrays.asList("신발"))
        );

        // 카테고리 저장
        for (CategoryData categoryData : categories) {
            Category parentCategory = new Category();
            parentCategory.setName(categoryData.getParentName());
            categoryRepository.save(parentCategory);

            for (String childName : categoryData.getChildNames()) {
                Category childCategory = new Category();
                childCategory.setName(childName);
                childCategory.setParent(parentCategory);
                parentCategory.getChildren().add(childCategory);
                categoryRepository.save(childCategory);
            }
        }

        System.out.println("Categories initialized.");
    }

    // 내부 클래스 또는 별도 파일로 데이터 구조 정의
    private static class CategoryData {
        private final String parentName;
        private final List<String> childNames;

        public CategoryData(String parentName, List<String> childNames) {
            this.parentName = parentName;
            this.childNames = childNames;
        }

        public String getParentName() {
            return parentName;
        }

        public List<String> getChildNames() {
            return childNames;
        }
    }
}
