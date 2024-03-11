package org.example.supplier.repository;

import org.example.supplier.CommonTest;
import org.example.supplier.model.entity.Product;
import org.example.supplier.service.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql({"/filter-products-data.sql"})
public class FilteredProductRepositoryImplTest extends CommonTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getFilteredProductsWhenEmptyResultExecutedCorrectly() {
        String name = "hello";
        String description = "";
        BigDecimal price = null;
        String categoryName = "";

        int page = 1;
        int size = 10;
        String[] sort = {"id", "asc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertThat(actual).isEmpty();
    }

    @Test
    public void getFilteredProductsWithNullFilterExecutedCorrectly() {
        String name = null;
        String description = null;
        BigDecimal price = null;
        String categoryName = null;

        int page = 1;
        int size = 10;
        String[] sort = {"id", "asc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(10, actual.getContent().size());
        assertEquals(1, actual.getContent().get(0).getId());
        assertEquals("white yellow blue red", actual.getContent().get(0).getDescription());
    }

    @Test
    public void getFilteredProductsWithEmptyStringsFiltersExecutedCorrectly() {
        String name = "";
        String description = "";
        BigDecimal price = null;
        String categoryName = "";

        int page = 1;
        int size = 10;
        String[] sort = {"id", "asc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(10, actual.getContent().size());
        assertEquals(1, actual.getContent().get(0).getId());
        assertEquals("white yellow blue red", actual.getContent().get(0).getDescription());
    }

    @Test
    public void getFilteredProductsWhenResultSortedAscExecutedCorrectly() {
        String name = null;
        String description = null;
        BigDecimal price = null;
        String categoryName = null;

        int page = 1;
        int size = 10;
        String[] sort = {"id", "asc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(10, actual.getContent().size());
        assertEquals(1, actual.getContent().get(0).getId());
        assertEquals("white yellow blue red", actual.getContent().get(0).getDescription());
    }

    @Test
    public void getFilteredProductsWhenResultSortedDescExecutedCorrectly() {
        String name = null;
        String description = null;
        BigDecimal price = null;
        String categoryName = null;

        int page = 1;
        int size = 10;
        String[] sort = {"id", "desc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(10, actual.getContent().size());
        assertEquals(10, actual.getContent().get(0).getId());
        assertEquals("red white", actual.getContent().get(0).getDescription());
    }

    @Test
    public void getFilteredProductsWhenResultSortedByTwoFieldsExecutedCorrectly() {
        String name = null;
        String description = null;
        BigDecimal price = null;
        String categoryName = null;

        int page = 1;
        int size = 10;
        String[] sort = {"category", "asc", "id", "desc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(10, actual.getContent().size());
        assertEquals(1, actual.getContent().get(0).getCategory().getId());
        assertEquals(1, actual.getContent().get(1).getCategory().getId());
        assertEquals(1, actual.getContent().get(0).getId());
        assertEquals(3, actual.getContent().get(1).getId());
    }

    @Test
    public void getFilteredProductsWhenPage1SelectedExecutedCorrectly() {
        String name = null;
        String description = null;
        BigDecimal price = null;
        String categoryName = null;

        int page = 1;
        int size = 5;
        String[] sort = {"id", "desc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(5, actual.getContent().size());
        assertIterableEquals(List.of(10L, 9L, 8L, 7L, 6L), actual.getContent().stream().map(Product::getId).collect(Collectors.toList()));

    }

    @Test
    public void getFilteredProductsWhenPage2SelectedExecutedCorrectly() {
        String name = null;
        String description = null;
        BigDecimal price = null;
        String categoryName = null;

        int page = 2;
        int size = 5;
        String[] sort = {"id", "desc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(5, actual.getContent().size());
        assertIterableEquals(List.of(5L, 4L, 3L, 2L, 1L), actual.getContent().stream().map(Product::getId).collect(Collectors.toList()));

    }

    @Test
    public void getFilteredProductsWhenPage3SelectedExecutedCorrectly() {
        String name = null;
        String description = null;
        BigDecimal price = null;
        String categoryName = null;

        int page = 3;
        int size = 5;
        String[] sort = {"id", "desc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(0, actual.getContent().size());
    }

    @Test
    public void getFilteredProductsFilteredByNameExecutedCorrectly() {
        String name = "u";
        String description = null;
        BigDecimal price = null;
        String categoryName = null;

        int page = 1;
        int size = 100;
        String[] sort = {"id", "desc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(2, actual.getContent().size());
        actual.getContent().stream().forEach(product -> assertTrue(product.getName().contains(name)));

    }

    @Test
    public void getFilteredProductsFilteredByDescriptionExecutedCorrectly() {
        String name = null;
        String description = "orange";
        BigDecimal price = null;
        String categoryName = null;

        int page = 1;
        int size = 100;
        String[] sort = {"id", "desc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(4, actual.getContent().size());
        actual.getContent().stream().forEach(product -> assertTrue(product.getDescription().contains(description)));
    }

    @Test
    public void getFilteredProductsFilteredByPriceExecutedCorrectly() {
        String name = null;
        String description = null;
        BigDecimal price = BigDecimal.valueOf(15);
        String categoryName = null;

        int page = 1;
        int size = 100;
        String[] sort = {"id", "desc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(2, actual.getContent().size());
        actual.getContent().stream().forEach(product -> assertEquals(0, product.getPrice().compareTo(price)));
    }

    @Test
    public void getFilteredProductsFilteredByCategoryNameExecutedCorrectly() {
        String name = null;
        String description = null;
        BigDecimal price = null;
        String categoryName = "2";

        int page = 1;
        int size = 100;
        String[] sort = {"id", "desc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(3, actual.getContent().size());
        actual.getContent().stream().forEach(product -> assertTrue(product.getCategory().getName().contains(categoryName)));
    }

    @Test
    public void getFilteredProductsFilteredByAllFieldsExecutedCorrectly() {
        String name = "e";
        String description = "white";
        BigDecimal price = BigDecimal.valueOf(20);
        String categoryName = "1";

        int page = 1;
        int size = 100;
        String[] sort = {"id", "desc"};
        Pageable pageable = Utils.getPageable(page, size, sort);

        Page<Product> actual = productRepository.getFilteredProducts(name, description, price, categoryName, pageable);

        assertEquals(1, actual.getContent().size());
        assertEquals(3, actual.getContent().get(0).getId());
    }

}