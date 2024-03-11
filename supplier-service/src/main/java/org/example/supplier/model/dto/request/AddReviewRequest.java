package org.example.supplier.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.supplier.model.entity.Product;
import org.example.supplier.model.entity.Review;

@Data
@NoArgsConstructor
public class AddReviewRequest {

    @Valid
    @NotBlank
    @JsonProperty("message")
    private String message;

    public Review toReview(Product product) {
        Review review = new Review();
        review.setMessage(this.message);
        review.setProduct(product);
        return review;
    }
}
