package org.example.customer.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.customer.model.dto.request.ProductRequest;
import org.example.customer.model.dto.response.GenericResponse;
import org.example.customer.model.entity.Product;
import org.example.customer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
@Slf4j
public class ProductRepositoryRestImpl extends PagingRestRepository implements ProductRepository {

    private final RestTemplate restTemplate;

    private final String supplierProductUrl;

    public ProductRepositoryRestImpl(RestTemplate restTemplate, @Value("${supplier.url}") String supplierRootUrl) {
        this.restTemplate = restTemplate;
        this.supplierProductUrl = supplierRootUrl + "/products";
    }

    @Override
    public GenericResponse<List<Product>> findAll(Integer page, Integer size, String[] sort) {
        String params = convertPagingParamsToString(page, size, sort);
        String remoteUrl = supplierProductUrl + params;
        ResponseEntity<GenericResponse> response = restTemplate.getForEntity(remoteUrl, GenericResponse.class);
        return response.getBody();
    }

    @Override
    public Product findById(Long productId) {
        String remoteUrl = supplierProductUrl + "/" + productId;
        ResponseEntity<Product> response = restTemplate.getForEntity(remoteUrl, Product.class);
        return response.getBody();
    }

    @Override
    public List<Product> saveAll(List<ProductRequest> productRequests) {
        String remoteUrl = supplierProductUrl;
        ResponseEntity<Product[]> response = restTemplate.postForEntity(remoteUrl, productRequests, Product[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public void update(Long productId, ProductRequest productRequest) {
        String remoteUrl = supplierProductUrl + "/" + productId;
        restTemplate.put(remoteUrl, productRequest);
    }

    @Override
    public void delete(Long productId) {
        String remoteUrl = supplierProductUrl + "/" + productId;
        restTemplate.delete(remoteUrl);
    }
}
