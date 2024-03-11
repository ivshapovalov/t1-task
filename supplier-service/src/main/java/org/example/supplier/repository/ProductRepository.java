package org.example.supplier.repository;

import org.example.supplier.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, FilteredProductRepository {

    @Override
    @Query("select p from Product p left join fetch p.reviews r left join fetch p.category c where p.id = :productId")
    Optional<Product> findById(@Param("productId") Long productId);
}
