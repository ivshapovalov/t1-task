package org.example.supplier.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.example.supplier.model.dto.request.ProductRequest;
import org.example.supplier.model.dto.request.AddReviewRequest;
import org.example.supplier.model.dto.response.GenericResponse;
import org.example.supplier.model.entity.Product;
import org.example.supplier.model.entity.Review;
import org.example.supplier.service.ProductService;
import org.example.supplier.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

    @RequestMapping(
            method = RequestMethod.POST,
            value = "",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<List<Product>> addProducts(
            @NotNull @Valid @RequestBody List<ProductRequest> products
    ) {
        return ResponseEntity.ok(productService.addProducts(products));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "",
            produces = {"application/json"}
    )
    ResponseEntity<GenericResponse> getProducts(
            @RequestParam(required = false) Map<String, String> parameters,
            @RequestParam(required = false, value = "page") Integer page,
            @RequestParam(required = false, value = "size") Integer size,
            @RequestParam(required = false, value = "sort") String[] sort
    ) {
        return ResponseEntity.ok(productService.getFilteredProducts(parameters, page, size, sort));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{productId}",
            produces = {"application/json"}
    )
    ResponseEntity<Product> getProduct(@PathVariable(value = "productId") @NotNull @Valid Long productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{productId}",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    ResponseEntity<Void> updateProduct(
            @PathVariable(value = "productId") @NotNull @Valid Long productId,
            @NotNull @Valid @RequestBody ProductRequest productRequest
    ) {
        productService.updateProduct(productId, productRequest);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{productId}",
            produces = {"application/json"}
    )
    ResponseEntity<Void> deleteProduct(@PathVariable(value = "productId") @NotNull @Valid Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/{productId}/reviews",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<List<Review>> addProductReviews(
            @PathVariable(value = "productId") @NotNull @Valid Long productId,
            @NotNull @Valid @RequestBody List<AddReviewRequest> reviews
    ) {
        return ResponseEntity.ok(reviewService.addReviews(productId, reviews));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{productId}/reviews",
            produces = {"application/json"}
    )
    ResponseEntity<GenericResponse> getProductReviews(
            @PathVariable(value = "productId") @NotNull @Valid Long productId,
            @RequestParam(required = false, value = "page") Integer page,
            @RequestParam(required = false, value = "size") Integer size,
            @RequestParam(required = false, value = "sort") String[] sort
    ) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId, page, size, sort));
    }

}
