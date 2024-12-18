package elice.webshopping.service.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import elice.webshopping.domain.category.Category;
import elice.webshopping.repository.category.CategoryRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveCategoryWithParent() {
        // Given
        Long parentId = 1L;
        String childName = "Child Category";
        Category parentCategory = new Category("Parent Category",parentId);

        when(categoryRepository.findById(parentId)).thenReturn(Optional.of(parentCategory));
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Category savedCategory = categoryService.save(childName, parentId);

        // Then
        assertNotNull(savedCategory);
        assertEquals(childName, savedCategory.getName());
        assertEquals(parentCategory, savedCategory.getParent());
        assertTrue(parentCategory.getChildren().contains(savedCategory));

        verify(categoryRepository, times(1)).findById(parentId);
        verify(categoryRepository, times(1)).save(savedCategory);
    }

    @Test
    void saveCategoryWithoutParent() {
        // Given
        String categoryName = "Parent Category";
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Category savedCategory = categoryService.save(categoryName, null);

        // Then
        assertNotNull(savedCategory);
        assertEquals(categoryName, savedCategory.getName());
        assertNull(savedCategory.getParent());

        verify(categoryRepository, never()).findById(anyLong());
        verify(categoryRepository, times(1)).save(savedCategory);
    }

    @Test
    void updateCategoryName() {
        // Given
        Long categoryId = 1L;
        String oldName = "Old Category Name";
        String newName = "New Category Name";
        Category category = new Category(oldName,categoryId);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        categoryService.update(newName, categoryId);

        // Then
        assertEquals(newName, category.getName());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void updateCategoryNameThrowsExceptionWhenNotFound() {
        // Given
        Long invalidId = 99L;
        String newName = "New Category Name";

        when(categoryRepository.findById(invalidId)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                categoryService.update(newName, invalidId));

        assertEquals("해당 ID가 없습니다.", exception.getMessage());
        verify(categoryRepository, times(1)).findById(invalidId);
    }
}
