package elice.webshopping.service.category;

import elice.webshopping.domain.category.Category;
import elice.webshopping.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
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
            Category parent = Category.from(categoryData.getParentName());
            categoryRepository.save(parent);

            for (String childName : categoryData.getChildNames()) {
                Category child = Category.from(childName);

                child.linkToParent(parent);
                parent.addChild(child);
                categoryRepository.save(child);
            }
            log.info("부모 테이블 :{}, 자식 개수 {}",parent.getName(),parent.getChildren().size());
        }
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
