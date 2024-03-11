package org.example.supplier.service;

import lombok.AllArgsConstructor;
import org.example.supplier.exceptions.ProductNotFoundException;
import org.example.supplier.model.dto.request.ProductRequest;
import org.example.supplier.model.dto.response.GenericResponse;
import org.example.supplier.model.entity.Category;
import org.example.supplier.model.entity.Product;
import org.example.supplier.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public List<Product> addProducts(List<ProductRequest> productsDtoList) {
        List<Product> products = productsDtoList.stream().map(productRequest -> {
            Category category = categoryService.getCategoryById(productRequest.getCategoryId());
            return productRequest.toProduct(category);
        }).collect(Collectors.toList());
        return productRepository.saveAll(products);
    }

    public GenericResponse<List<Product>> getFilteredProducts(Map<String, String> parameters, Integer page, Integer size, String[] sort) {
        Pageable pageable = Utils.getPageable(page, size, sort);
        String name = parameters.get("name");
        String description = parameters.get("description");
        BigDecimal price = Objects.isNull(parameters.get("price")) ? null : new BigDecimal(parameters.get("price"));
        String categoryName = parameters.get("category");

        Page<Product> pageProducts =
                productRepository.getFilteredProducts(name, description, price, categoryName, pageable);
        return new GenericResponse<>(pageProducts.getContent(), Utils.getPagingInfoFromPage(pageProducts));

    }

    public Product getProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(productId);
        }
        return optionalProduct.get();
    }

    public void updateProduct(Long productId, ProductRequest productRequest) {
        getProduct(productId);
        Category category = categoryService.getCategoryById(productRequest.getCategoryId());
        Product product = productRequest.toProduct(category);
        product.setId(productId);
        productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        Product existedProduct = getProduct(productId);
        productRepository.delete(existedProduct);
    }


}
