package org.example.customer.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.customer.model.dto.request.CategoryRequest;
import org.example.customer.model.dto.response.GenericResponse;
import org.example.customer.model.entity.Category;
import org.example.customer.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
@Slf4j
public class CategoryRepositoryPagingRestImpl extends PagingRestRepository implements CategoryRepository {

    private final RestTemplate restTemplate;

    private final String supplierCategoryUrl;

    public CategoryRepositoryPagingRestImpl(RestTemplate restTemplate, @Value("${supplier.url}") String supplierRootUrl) {
        this.restTemplate = restTemplate;
        this.supplierCategoryUrl = supplierRootUrl + "/categories";
    }

    @Override
    public GenericResponse<List<Category>> findAll(Integer page, Integer size, String[] sort) {
        String params = convertPagingParamsToString(page, size, sort);
        String remoteUrl = supplierCategoryUrl+params;
        ResponseEntity<GenericResponse> response = restTemplate.getForEntity(remoteUrl, GenericResponse.class);
        return response.getBody();
    }

    @Override
    public Category findById(Long categoryId) {
        String remoteUrl = supplierCategoryUrl + "/" + categoryId;
        ResponseEntity<Category> response = restTemplate.getForEntity(remoteUrl, Category.class);
        return response.getBody();
    }

    @Override
    public List<Category> saveAll(List<CategoryRequest> categoryRequests) {
        String remoteUrl = supplierCategoryUrl;
        ResponseEntity<Category[]> response = restTemplate.postForEntity(remoteUrl, categoryRequests, Category[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public void update(Long categoryId, CategoryRequest categoryRequest) {
        String remoteUrl = supplierCategoryUrl + "/" + categoryId;
        restTemplate.put(remoteUrl, categoryRequest);
    }

    @Override
    public void delete(Long categoryId) {
        String remoteUrl = supplierCategoryUrl + "/" + categoryId;
        restTemplate.delete(remoteUrl);
    }
}
