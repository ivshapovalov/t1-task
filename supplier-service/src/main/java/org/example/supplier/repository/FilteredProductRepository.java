package org.example.supplier.repository;

import org.example.supplier.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface FilteredProductRepository {

    Page<Product> getFilteredProducts(String name,
                                      String description,
                                      BigDecimal price,
                                      String categoryName,
                                      Pageable pageable);
}