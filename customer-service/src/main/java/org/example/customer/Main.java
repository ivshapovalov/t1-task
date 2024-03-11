package org.example.customer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.customer.exceptions.ApiError;
import org.example.customer.model.dto.request.AddReviewRequest;
import org.example.customer.model.dto.request.CategoryRequest;
import org.example.customer.model.dto.request.ProductRequest;
import org.example.customer.model.dto.request.UpdateReviewRequest;
import org.example.customer.model.dto.response.GenericResponse;
import org.example.customer.model.entity.Category;
import org.example.customer.model.entity.Product;
import org.example.customer.model.entity.Review;
import org.example.customer.service.CategoryService;
import org.example.customer.service.ProductService;
import org.example.customer.service.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Validated
@Service
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class Main {

    CategoryService categoryService;

    ProductService productService;

    ReviewService reviewService;

    ObjectMapper objectMapper;

    public void main() {

        try {
            testCategoriesMethods();
            testReviewsMethods();
            testProductsMethods();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void testCategoriesMethods() throws JsonProcessingException {

        log.info("************************************CATEGORIES STARTED**********************************************");

        CategoryRequest categoryRequest1 = new CategoryRequest("Category 1");
        CategoryRequest categoryRequest2 = new CategoryRequest("Category 2");
        CategoryRequest categoryRequest3 = new CategoryRequest("Category 3");
        List<CategoryRequest> categoryRequests = List.of(categoryRequest1, categoryRequest2, categoryRequest3);
        log.info("AddCategories request: categoryRequests {}", categoryRequests);
        List<Category> addCategories = categoryService.addCategories(categoryRequests);
        log.info("AddCategories response: {}", addCategories);

        log.info("GetCategory request: id {}", 1);
        Category category = categoryService.getCategory(2L);
        log.info("GetCategory response : {}", category);

        log.info("GetCategories request: page {} size {}, sort '{}'", 1, 5, "name,desc");
        GenericResponse<List<Category>> getCategories = categoryService.getCategories(1, 5, new String[]{"name", "desc"});
        log.info("GetCategories response: {}", getCategories);

        CategoryRequest categoryRequestToUpdate = new CategoryRequest("Category to update ");
        Category categoryToUpdate = categoryService.addCategories(List.of(categoryRequestToUpdate)).get(0);
        log.info("UpdateCategory request: category before update {}", categoryToUpdate);
        CategoryRequest categoryRequest4_1 = new CategoryRequest("Category updated");
        categoryService.updateCategory(categoryToUpdate.getId(), categoryRequest4_1);
        Category categoryAfterUpdate = categoryService.getCategory(categoryToUpdate.getId());
        log.info("UpdateCategory request: category after update {}", categoryAfterUpdate);

        CategoryRequest categoryRequestToDelete = new CategoryRequest("Category to delete");
        Category categoryToDelete = categoryService.addCategories(List.of(categoryRequestToUpdate)).get(0);
        log.info("DeleteCategory request: category before delete {}", categoryToDelete);
        categoryService.deleteCategory(categoryToDelete.getId());
        try {
            categoryService.getCategory(categoryToDelete.getId());
        } catch (HttpClientErrorException ex) {
            log.info("DeleteCategory response catch HttpClientErrorException");
            ApiError error = convertToApiError(ex);
            log.error(error.toString());
        }

        log.info("************************************CATEGORIES FINISHED**********************************************");


    }

    private void testReviewsMethods() throws JsonProcessingException {

        log.info("************************************REVIEWS STARTED**********************************************");

        log.info("GetReview request: id {}", 1);
        Review review = reviewService.getReview(2L);
        log.info("GetReview response : {}", review);

        AddReviewRequest addReviewRequestToUpdate = new AddReviewRequest("Review to update");
        Review reviewToUpdate = reviewService.addProductReviews(3L, List.of(addReviewRequestToUpdate)).get(0);
        log.info("UpdateReview request: review before update {}", reviewToUpdate);
        UpdateReviewRequest updateReviewRequest = new UpdateReviewRequest("Category updated", 3L);
        reviewService.updateReview(reviewToUpdate.getId(), updateReviewRequest);
        Review reviewAfterUpdate = reviewService.getReview(reviewToUpdate.getId());
        log.info("UpdateReview request: review after update {}", reviewAfterUpdate);

        AddReviewRequest addReviewRequestToDelete = new AddReviewRequest("Review to delete");
        Review reviewToDelete = reviewService.addProductReviews(4L, List.of(addReviewRequestToDelete)).get(0);
        log.info("DeleteReview request: review before delete {}", reviewToDelete);
        reviewService.deleteReview(reviewToDelete.getId());
        try {
            reviewService.getReview(reviewToDelete.getId());
        } catch (HttpClientErrorException ex) {
            log.info("DeleteReview response catch HttpClientErrorException");
            ApiError error = convertToApiError(ex);
            log.error(error.toString());
        }

        CategoryRequest addCategoryRequest = new CategoryRequest("Category 1");
        List<Category> addedCategories = categoryService.addCategories(List.of(addCategoryRequest));
        List<Product> products = productService.addProducts(List.of(
                new ProductRequest("name1", "desc1", new BigDecimal(10), addedCategories.get(0).getId())));
        AddReviewRequest addReviewRequestToRetrieve2 = new AddReviewRequest("Review 125");
        AddReviewRequest addReviewRequestToRetrieve1 = new AddReviewRequest("Review 255");
        List<AddReviewRequest> addReviewRequests = List.of(addReviewRequestToRetrieve1, addReviewRequestToRetrieve2);
        log.info("addProductReviews request: product Id {}, reviews requests {}", products.get(0).getId(), addReviewRequests);
        List<Review> reviewsSaved = reviewService.addProductReviews(products.get(0).getId(), addReviewRequests);
        log.info("addProductReviews response:  {}", reviewsSaved);
        log.info("getProductReviews request: product Id {}, page 1, size 5, sort 'message,desc'", products.get(0).getId());
        GenericResponse<List<Review>> reviewsGot =
                reviewService.getProductReviews(products.get(0).getId(), 1,5,new String[]{"message","desc"});
        log.info("getProductReviews response: {}", reviewsGot);

        log.info("************************************REVIEWS FINISHED**********************************************");

    }


    private void testProductsMethods() throws JsonProcessingException {

        log.info("************************************PRODUCTS STARTED**********************************************");


        CategoryRequest addCategoryRequest1 = new CategoryRequest("Category 1");
        CategoryRequest addCategoryRequest2 = new CategoryRequest("Category 2");
        List<Category> addedCategories = categoryService.addCategories(List.of(addCategoryRequest1,addCategoryRequest2));
        ProductRequest productRequest1 = new ProductRequest("name1", "desc1", new BigDecimal(10), addedCategories.get(0).getId());
        ProductRequest productRequest2 = new ProductRequest("name2", "desc2", new BigDecimal(15), addedCategories.get(1).getId());
        List<ProductRequest> productRequests = List.of(productRequest1, productRequest2);
        log.info("AddProducts request: productRequest {}", productRequests);
        List<Product> addedProducts = productService.addProducts(productRequests);
        log.info("AddProducts response: {}", addedProducts);

        log.info("GetProduct request: id {}", 1);
        Product product = productService.getProduct(addedProducts.get(0).getId());
        log.info("GetProduct response : {}", product);

        log.info("GetProducts request: page {} size {}, sort '{}'", 1, 5, "name,desc");
        GenericResponse<List<Product>> products = productService.getProducts(1, 5, new String[]{"id", "desc"});
        log.info("GetProducts response: {}", products);

        ProductRequest productRequestToUpdate =
                new ProductRequest("name1-1", "desc1-1", new BigDecimal(11), addedCategories.get(1).getId());
        log.info("UpdateProduct request: product before update {}", addedProducts.get(0));
        productService.updateProduct(addedProducts.get(0).getId(),productRequestToUpdate);
        Product productAfterUpdate = productService.getProduct(addedProducts.get(0).getId());
        log.info("UpdateProduct request: product after update {}", productAfterUpdate);

        ProductRequest productRequestToDelete = new ProductRequest("nameToDelete", "descToDelete",
                new BigDecimal(15), addedCategories.get(0).getId());
        Product productToDelete = productService.addProducts(List.of(productRequestToDelete)).get(0);
        log.info("DeleteProduct request: product before delete {}", productToDelete);
        productService.deleteProduct(productToDelete.getId());
        try {
            productService.getProduct(productToDelete.getId());
        } catch (HttpClientErrorException ex) {
            log.info("DeleteProduct response catch HttpClientErrorException");
            ApiError error = convertToApiError(ex);
            log.error(error.toString());

        }
        log.info("************************************PRODUCTS FINISHED**********************************************");
    }


    private ApiError convertToApiError(HttpClientErrorException ex) throws JsonProcessingException {
        return objectMapper.readValue(ex.getResponseBodyAsString(), ApiError.class);
    }

}
