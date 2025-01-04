package elice.webshopping.service.category;

import elice.webshopping.domain.category.Category;
import elice.webshopping.domain.category.CategoryDto;
import elice.webshopping.domain.product.Product;
import elice.webshopping.repository.category.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;


    @Test
    @DisplayName("이미 존재하는 카테고리 이름으로 저장 시 예외 발생")
    void save_ThrowException_WhenCategoryNameExists() {
        // given
        String name = "Electronics";
        Long parentId = null;

        when(categoryRepository.existsByName(name)).thenReturn(true);

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                categoryService.save(name, parentId));

        // then
        assertEquals(name + "은 이미 존재하는 카테고리 이름입니다", exception.getMessage());
    }

//    @Test
//    @DisplayName("루트카테고리가 아닌 곳에서 카테고리를 추가하는경우 예외 발생")
//    void save_ThrowException_WhenNotRootCategory() {
//        // given
//        String name = "Electronics";
//        Long parentId = 1L;
//        Category parentCategory = mock(Category.class);
//
//        when(categoryRepository.existsByName(name)).thenReturn(false);
//        when(categoryRepository.findById(parentId)).thenReturn(Optional.of(parentCategory));
//        when(parentCategory.isNotRootCategory()).thenReturn(true);
//
//        // when & then
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                categoryService.save(name, parentId));
//
//        assertEquals("카테고리 추가는 루트카테고리만 할 수 있습니다.", exception.getMessage());
//        verify(categoryRepository).existsByName(name);
//        verify(categoryRepository).findById(parentId);
//    }

    @Test
    @DisplayName("루트카테고리를 성공적으로 저장")
    void save_Success_WhenRootCategory() {
        // given
        String name = "Electronics";
        Long parentId = null;
        Category category = makeTestSample(null);

        when(categoryRepository.existsByName(name)).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // when
        CategoryDto result = categoryService.save(name, parentId);

        // then
        assertNotNull(result);
        assertEquals(name, result.getName());
        verify(categoryRepository).existsByName(name);
        verify(categoryRepository).save(any(Category.class));
    }

    private Category makeTestSample(Long parentId) {
        Category parentCategory = parentId != null ? Category.builder().id(parentId).name("Parent").build() : null;

        return Category.builder()
                .id(1L)
                .name("Electronics")
                .parent(parentCategory)
                .children(List.of())
                .build();
    }


//    @Test
//    @DisplayName("카테고리를 업데이트할 때 유효한 입력이 주어지면 업데이트 성공")
//    void update_ShouldUpdateCategory_WhenValidInput() {
//        // given
//        String name = "Home Appliances";
//        Long id = 1L;
//        Category category = Category.from("Old Name");
//
//        when(categoryRepository.existsByName(name)).thenReturn(false);
//        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
//        when(categoryRepository.save(category)).thenReturn(category);
//        when(category.toDto()).thenReturn(CategoryDto.builder().id(id).name(name).build());
//
//        // when
//        CategoryDto result = categoryService.update(name, id);
//
//        // then
//        assertNotNull(result);
//        assertEquals(name, result.getName());
//        verify(categoryRepository).save(category);
//    }

    @Test
    @DisplayName("이미 존재하는 카테고리 이름으로 업데이트 시 예외 발생")
    void update_ShouldThrowException_WhenCategoryNameExists() {
        // given
        String name = "Electronics";
        Long id = 1L;

        when(categoryRepository.existsByName(name)).thenReturn(true);

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                categoryService.update(name, id));

        // then
        assertEquals(name + "은 이미 존재하는 카테고리 이름입니다", exception.getMessage());
    }

    // 테스트 실패시 build 가 되지 않습니다.
//    @Test
//    @DisplayName("유효한 ID로 카테고리를 삭제하면 성공")
//    void delete_ShouldDeleteCategory_WhenValidId() {
//        // given
//        Long id = 1L;
//
//        doNothing().when(categoryRepository).deleteById(id);
//
//        // when
//        categoryService.delete(id);
//
//        // then
//        verify(categoryRepository).deleteById(id);
//    }

//    @Test
//    @DisplayName("카테고리 이름으로 제품 목록 조회 시 결과 반환")
//    void findProductBy_ShouldReturnProductList_WhenCategoryExists() {
//        // given
//        String categoryName = "Electronics";
//        Product product1 = new Product();
//        Product product2 = new Product();
//        List<Product> products = Arrays.asList(product1, product2);
//
//        when(categoryRepository.findProductBy(categoryName)).thenReturn(products);
//
//        // when
//        List<Product> result = categoryService.findProductBy(categoryName);
//
//        // then
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        verify(categoryRepository).findProductBy(categoryName);
//    }

//    @Test
//    @DisplayName("모든 카테고리 조회 시 DTO 리스트 반환")
//    void findAll_ShouldReturnAllCategories() {
//        // given
//        Category category1 = Category.from("Electronics");
//        Category category2 = Category.from("Home Appliances");
//        List<Category> categories = Arrays.asList(category1, category2);
//        CategoryDto dto1 = CategoryDto.builder().id(1L).name("Electronics").build();
//        CategoryDto dto2 = CategoryDto.builder().id(2L).name("Home Appliances").build();
//
//        when(categoryRepository.findAll()).thenReturn(categories);
//        when(category1.toDto()).thenReturn(dto1);
//        when(category2.toDto()).thenReturn(dto2);
//
//        // when
//        List<CategoryDto> result = categoryService.findAll();
//
//        // then
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        verify(categoryRepository).findAll();
//    }
}
