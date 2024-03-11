package org.example.supplier.service;

import org.example.supplier.exceptions.CategoryNotFoundException;
import org.example.supplier.model.dto.request.CategoryRequest;
import org.example.supplier.model.dto.response.GenericResponse;
import org.example.supplier.model.entity.Category;
import org.example.supplier.repository.CategoryRepository;
import org.example.supplier.repository.ProductRepository;
import org.example.supplier.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class CategoryServiceTest {

    @Autowired
    @InjectMocks
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ReviewRepository reviewRepository;

    @Test
    public void addCategoryWhenOneCategoryOk() {
        CategoryRequest categoryRequest = new CategoryRequest("Category 1");
        List<Category> expected = List.of(categoryRequest.toCategory());

        when(categoryRepository.saveAll(any())).thenReturn(expected);

        List<Category> actual = categoryService.addCategories(List.of(categoryRequest));

        verify(categoryRepository).saveAll(any());
        ArgumentCaptor<List<Category>> captor = ArgumentCaptor.forClass(List.class);
        verify(categoryRepository).saveAll(captor.capture());
        assertIterableEquals(expected, captor.getValue());
        assertIterableEquals(expected, actual);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void addCategoryWhenThreeCategoryOk() {
        CategoryRequest categoryRequest1 = new CategoryRequest("Category 1");
        CategoryRequest categoryRequest2 = new CategoryRequest("Category 2");
        CategoryRequest categoryRequest3 = new CategoryRequest("Category 3");
        List<Category> expected = List.of(categoryRequest1.toCategory(), categoryRequest2.toCategory(), categoryRequest3.toCategory());

        when(categoryRepository.saveAll(any())).thenReturn(expected);

        List<Category> actual = categoryService.addCategories(List.of(categoryRequest1, categoryRequest2, categoryRequest3));

        verify(categoryRepository).saveAll(any());
        ArgumentCaptor<List<Category>> captor = ArgumentCaptor.forClass(List.class);
        verify(categoryRepository).saveAll(captor.capture());
        assertIterableEquals(expected, captor.getValue());
        assertIterableEquals(expected, actual);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void getCategoriesOk() {
        int page = 1;
        int size = 10;
        String[] sort = {"id,desc"};
        List<Category> categories = List.of(new Category(1L, "Category 1"), new Category(2L, "Category 2"), new Category(3L, "Category 3"));

        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(new Sort.Order(DESC, "id"))));
        PageImpl<Category> pageCategories = new PageImpl<>(categories, pageable, 1);

        GenericResponse expected = new GenericResponse();
        expected.setResult(categories);
        expected.setPagingInfo(Utils.getPagingInfoFromPage(pageCategories));

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(pageCategories);

        GenericResponse<List<Category>> actual = categoryService.getCategories(page, size, sort);

        verify(categoryRepository).findAll(any(Pageable.class));
        assertEquals(expected, actual);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void getCategoriesReturnEmptyArray() {
        int page = 1;
        int size = 10;
        String[] sort = {"id,desc"};
        List<Category> categories = Collections.emptyList();

        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(new Sort.Order(DESC, "id"))));
        PageImpl<Category> pageCategories = new PageImpl<>(categories, pageable, 1);

        GenericResponse expected = new GenericResponse();
        expected.setResult(categories);
        expected.setPagingInfo(Utils.getPagingInfoFromPage(pageCategories));

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(pageCategories);

        GenericResponse<List<Category>> actual = categoryService.getCategories(page, size, sort);

        verify(categoryRepository).findAll(any(Pageable.class));
        assertEquals(expected, actual);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void getCategoryByIdWhenCategoryExists() {
        Long categoryId = 1L;
        Category expected = new Category(1L, "Category 1");

        when(categoryRepository.findById(any())).thenReturn(Optional.of(expected));

        Category actual = categoryService.getCategoryById(categoryId);

        verify(categoryRepository).findById(any(Long.class));
        assertEquals(expected, actual);
        verifyNoMoreInteractions(categoryRepository);
    }

    @Test
    public void getCategoryByIdWhenCategoryNotExists() {
        Long categoryId = 1L;

        when(categoryRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById(categoryId));

        verify(categoryRepository).findById(any(Long.class));
        verifyNoMoreInteractions(categoryRepository);
    }

}
