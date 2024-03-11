package org.example.customer.repository;

import org.example.customer.model.dto.request.AddReviewRequest;
import org.example.customer.model.dto.request.UpdateReviewRequest;
import org.example.customer.model.dto.response.GenericResponse;
import org.example.customer.model.entity.Review;

import java.util.List;

public interface ReviewRepository {

    Review findById(Long reviewId);

    void update(Long reviewId, UpdateReviewRequest updateReviewRequest);

    void delete(Long reviewId);

    GenericResponse<List<Review>> findAllProductReviews(Long productId, Integer page, Integer size, String[] sort);

    List<Review> saveProductReviews(Long productId, List<AddReviewRequest> addReviewRequests);

}
