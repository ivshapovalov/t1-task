package org.example.customer.service;

import lombok.AllArgsConstructor;
import org.example.customer.model.dto.request.AddReviewRequest;
import org.example.customer.model.dto.request.UpdateReviewRequest;
import org.example.customer.model.dto.response.GenericResponse;
import org.example.customer.model.entity.Review;
import org.example.customer.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId);
    }

    public void updateReview(Long reviewId, UpdateReviewRequest updateReviewRequest) {
        reviewRepository.update(reviewId, updateReviewRequest);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.delete(reviewId);
    }

    public GenericResponse<List<Review>> getProductReviews(Long productId, Integer page, Integer size, String[] sort) {
        return reviewRepository.findAllProductReviews(productId, page, size, sort);
    }

    public List<Review> addProductReviews(Long productId, List<AddReviewRequest> addReviewRequests) {
        return reviewRepository.saveProductReviews(productId, addReviewRequests);
    }
}
