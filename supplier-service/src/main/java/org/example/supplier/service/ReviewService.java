package org.example.supplier.service;

import lombok.AllArgsConstructor;
import org.example.supplier.exceptions.ReviewNotFoundException;
import org.example.supplier.model.dto.request.AddReviewRequest;
import org.example.supplier.model.dto.request.UpdateReviewRequest;
import org.example.supplier.model.dto.response.GenericResponse;
import org.example.supplier.model.entity.Product;
import org.example.supplier.model.entity.Review;
import org.example.supplier.repository.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    public List<Review> addReviews(Long productId, List<AddReviewRequest> addReviewRequestList) {
        Product product = productService.getProduct(productId);
        List<Review> reviews = addReviewRequestList.stream()
                .map(addReviewRequest -> {
                    Review review = addReviewRequest.toReview(product);
                    review.setProduct(product);
                    return review;
                })
                .collect(Collectors.toList());
        return reviewRepository.saveAll(reviews);
    }

    public Review getReviewById(Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalReview.isEmpty()) {
            throw new ReviewNotFoundException(reviewId);
        }
        return optionalReview.get();
    }

    public GenericResponse<List<Review>> getReviewsByProductId(Long productId, Integer page, Integer size, String[] sort) {
        Pageable pageable = Utils.getPageable(page, size, sort);
        Page<Review> pageReviews = reviewRepository.findAllByProductId(productId, pageable);
        return new GenericResponse<>(pageReviews.getContent(), Utils.getPagingInfoFromPage(pageReviews));
    }

    public void updateReview(Long reviewId, UpdateReviewRequest updateReviewRequest) {
        Product product = productService.getProduct(updateReviewRequest.getProductId());
        getReviewById(reviewId);
        Review review = updateReviewRequest.toReview(product);
        review.setId(reviewId);
        reviewRepository.save(review);
    }

    public void deleteReview(Long reviewId) {
        Review existedReview = getReviewById(reviewId);
        reviewRepository.delete(existedReview);
    }

}
