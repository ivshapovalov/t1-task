package org.example.supplier.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.supplier.model.dto.request.UpdateReviewRequest;
import org.example.supplier.model.entity.Review;
import org.example.supplier.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{reviewId}",
            produces = {"application/json"}
    )
    ResponseEntity<Review> getReview(
            @PathVariable(value = "reviewId") @NotNull @Valid Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{reviewId}",
            consumes = {"application/json"},
            produces = {"application/json"}
    )
    ResponseEntity<Void> updateReview(
            @PathVariable(value = "reviewId") @NotNull @Valid Long reviewId,
            @NotNull @Valid @RequestBody UpdateReviewRequest updateReviewRequest
    ) {
        reviewService.updateReview(reviewId, updateReviewRequest);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{reviewId}",
            produces = {"application/json"}
    )
    ResponseEntity<Void> deleteReview(
            @PathVariable(value = "reviewId") @NotNull @Valid Long reviewId
    ) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

}
