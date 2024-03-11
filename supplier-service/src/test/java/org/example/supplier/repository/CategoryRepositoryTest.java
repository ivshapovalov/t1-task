package org.example.supplier.repository;

import org.example.supplier.CommonTest;
import org.example.supplier.model.entity.Category;
import org.example.supplier.service.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.Sort.Direction.DESC;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CategoryRepositoryTest extends CommonTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findAllWhenNoOneCategoryExistsExecutedCorrectly() {
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size, Sort.by(List.of(new Sort.Order(DESC, "id"))));

        Page<Category> categories = categoryRepository.findAll(pageable);
        assertThat(categories).isEmpty();
    }

    @Test
    public void findAllWhenOneCategoryExistsExecutedCorrectly() {
        int page = 1;
        int size = 10;
        Pageable pageable = Utils.getPageable(page, size, new String[]{"id", "desc"});

        Category category1 = new Category(1, "Category 1");
        categoryRepository.saveAndFlush(category1);

        Page<Category> categories = categoryRepository.findAll(pageable);

        assertEquals(1, categories.getContent().size());
        assertEquals(category1, categories.getContent().get(0));
    }

    @Test
    public void findAllWhenTwoCategoriseExistAndSortAscAndPageOneSelectedExecutedCorrectly() {
        int page = 1;
        int size = 1;
        Pageable pageable = Utils.getPageable(page, size, new String[]{"id", "asc"});

        Category category1 = new Category(1, "Category 1");
        Category category2 = new Category(2, "Category 2");
        categoryRepository.saveAllAndFlush(List.of(category1, category2));

        Page<Category> categories = categoryRepository.findAll(pageable);

        assertEquals(1, categories.getContent().size());
        assertEquals(category1, categories.getContent().get(0));
    }

    @Test
    public void findAllWhenTwoCategoriseExistAndSortAscAndPageTwoSelectedExecutedCorrectly() {
        int page = 2;
        int size = 1;
        Pageable pageable = Utils.getPageable(page, size, new String[]{"id", "asc"});

        Category category1 = new Category(1, "Category 1");
        Category category2 = new Category(2, "Category 2");
        categoryRepository.saveAllAndFlush(List.of(category1, category2));

        Page<Category> categories = categoryRepository.findAll(pageable);

        assertEquals(1, categories.getContent().size());
        assertEquals(category2, categories.getContent().get(0));
    }

    @Test
    public void findAllWhenTwoCategoriseExistAndSortDescAndPageOneSelectedExecutedCorrectly() {
        int page = 1;
        int size = 1;
        Pageable pageable = Utils.getPageable(page, size, new String[]{"id", "desc"});

        Category category1 = new Category(1, "Category 1");
        Category category2 = new Category(2, "Category 2");
        categoryRepository.saveAllAndFlush(List.of(category1, category2));

        Page<Category> categories = categoryRepository.findAll(pageable);

        assertEquals(1, categories.getContent().size());
        assertEquals(category2, categories.getContent().get(0));
    }

    @Test
    public void findAllWhenTwoCategoriseExistAndSortAscAndPageThreeSelectedExecutedCorrectly() {
        int page = 3;
        int size = 1;
        Pageable pageable = Utils.getPageable(page, size, new String[]{"id", "asc"});

        Category category1 = new Category(1, "Category 1");
        Category category2 = new Category(2, "Category 2");
        categoryRepository.saveAllAndFlush(List.of(category1, category2));

        Page<Category> categories = categoryRepository.findAll(pageable);

        assertEquals(0, categories.getContent().size());
    }

}