package org.example.supplier.service;

import lombok.AllArgsConstructor;
import org.example.supplier.exceptions.CategoryNotFoundException;
import org.example.supplier.model.dto.request.CategoryRequest;
import org.example.supplier.model.dto.response.GenericResponse;
import org.example.supplier.model.entity.Category;
import org.example.supplier.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> addCategories(List<CategoryRequest> categoryRequestList) {
        List<Category> categories = categoryRequestList.stream()
                .map(CategoryRequest::toCategory)
                .collect(Collectors.toList());
        return categoryRepository.saveAll(categories);
    }

    public GenericResponse<List<Category>> getCategories(Integer page, Integer size, String[] sort) {
        Pageable pageable = Utils.getPageable(page, size, sort);
        Page<Category> pageCategories = categoryRepository.findAll(pageable);
        return new GenericResponse<>(pageCategories.getContent(), Utils.getPagingInfoFromPage(pageCategories));
    }

    public Category getCategoryById(Long categoryId) {

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(categoryId);
        }
        return optionalCategory.get();
    }

    public void updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        getCategoryById(categoryId);
        Category category = categoryRequest.toCategory();
        category.setId(categoryId);
        categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        Category existedCategory = getCategoryById(categoryId);
        categoryRepository.delete(existedCategory);
    }
}
