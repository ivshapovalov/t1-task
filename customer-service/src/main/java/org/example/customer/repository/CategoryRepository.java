package org.example.customer.repository;

import org.example.customer.model.dto.request.CategoryRequest;
import org.example.customer.model.dto.response.GenericResponse;
import org.example.customer.model.entity.Category;

import java.util.List;

public interface CategoryRepository {
    GenericResponse<List<Category>> findAll(Integer page, Integer size, String[] sort);

    Category findById(Long categoryId);

    List<Category> saveAll(List<CategoryRequest> categories);

    void update(Long categoryId, CategoryRequest categoryRequest);

    void delete(Long categoryId);
}
