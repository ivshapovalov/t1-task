package org.example.supplier.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.supplier.model.dto.request.CategoryRequest;
import org.example.supplier.model.dto.response.GenericResponse;
import org.example.supplier.model.entity.Category;
import org.example.supplier.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<List<Category>> addCategories(
            @NotNull @Valid @RequestBody List<CategoryRequest> categoryRequestList
    ) {
        return ResponseEntity.ok(categoryService.addCategories(categoryRequestList));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "",
            produces = {"application/json"}
    )
    ResponseEntity<GenericResponse<List<Category>>> getCategories(
            @RequestParam(required = false, value = "page") Integer page,
            @RequestParam(required = false, value = "size") Integer size,
            @RequestParam(required = false, value = "sort") String[] sort
    ) {
        return ResponseEntity.ok(categoryService.getCategories(page, size, sort));
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{id}",
            produces = {"application/json"}
    )
    ResponseEntity<Category> getCategory(
            @PathVariable(value = "id") @NotNull @Valid Long categoryId
    ) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{id}",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    ResponseEntity<Category> updateCategory(
            @PathVariable(value = "id") @NotNull @Valid Long categoryId,
            @NotNull @Valid @RequestBody CategoryRequest categoryRequest
    ) {
        categoryService.updateCategory(categoryId, categoryRequest);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{id}",
            produces = {"application/json"}
    )
    ResponseEntity<Void> deleteCategory(@PathVariable(value = "id") @NotNull @Valid Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }

}
