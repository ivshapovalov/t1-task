package org.example.customer.service;

import lombok.AllArgsConstructor;
import org.example.customer.model.dto.request.ProductRequest;
import org.example.customer.model.dto.response.GenericResponse;
import org.example.customer.model.entity.Product;
import org.example.customer.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> addProducts(List<ProductRequest> productRequests) {
        return productRepository.saveAll(productRequests);
    }

    public GenericResponse<List<Product>> getProducts(Integer page, Integer size, String[] sort) {
        return productRepository.findAll(page, size, sort);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    public void updateProduct(Long productId, ProductRequest productRequest) {
        productRepository.update(productId, productRequest);
    }

    public void deleteProduct(Long productId) {
        productRepository.delete(productId);
    }

}
