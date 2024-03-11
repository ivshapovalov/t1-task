package org.example.customer.repository;

import org.example.customer.model.dto.request.ProductRequest;
import org.example.customer.model.dto.response.GenericResponse;
import org.example.customer.model.entity.Product;

import java.util.List;

public interface ProductRepository {
    GenericResponse<List<Product>> findAll(Integer page, Integer size, String[] sort);

    Product findById(Long productId);

    List<Product> saveAll(List<ProductRequest> productRequests);

    void update(Long productId, ProductRequest productRequest);

    void delete(Long productId);


}
