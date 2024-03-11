package org.example.customer.service;

import lombok.AllArgsConstructor;
import org.example.customer.model.dto.request.CategoryRequest;
import org.example.customer.model.dto.response.GenericResponse;
import org.example.customer.model.entity.Category;
import org.example.customer.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> addCategories(List<CategoryRequest> categoryRequests) {
        return categoryRepository.saveAll(categoryRequests);
    }

    public GenericResponse<List<Category>> getCategories(Integer page, Integer size, String[] sort) {
        return categoryRepository.findAll(page, size, sort);
    }

    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public void updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        categoryRepository.update(categoryId, categoryRequest);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.delete(categoryId);
    }
}
