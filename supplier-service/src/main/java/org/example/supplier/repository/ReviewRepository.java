package org.example.supplier.repository;

import org.example.supplier.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r from Review r join fetch r.product p join fetch p.category where p.id=?1")
    Page<Review> findAllByProductId(Long productId, Pageable pageable);

    @Query("select r from Review r join fetch r.product p join fetch p.category where r.id=?1")
    Optional<Review> findById(Long reviewId);

}
