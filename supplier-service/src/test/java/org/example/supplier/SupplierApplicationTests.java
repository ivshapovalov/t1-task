package org.example.supplier;

import org.example.supplier.controller.CategoryController;
import org.example.supplier.controller.ProductController;
import org.example.supplier.controller.ReviewController;
import org.example.supplier.service.CategoryService;
import org.example.supplier.service.ProductService;
import org.example.supplier.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class})
class SupplierApplicationTests extends CommonTest {

    @Autowired
    private CategoryController categoryController;

    @Autowired
    private ProductController productController;

    @Autowired
    private ReviewController reviewController;

    @MockBean
    private CategoryService categoryService;
    @MockBean
    private ProductService productService;
    @MockBean
    private ReviewService reviewService;

    @Test
    public void contextLoads() {
        assertNotNull(categoryController);
        assertNotNull(productController);
        assertNotNull(reviewController);
    }
}
