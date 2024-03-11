package org.example.supplier.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.supplier.model.entity.Product;
import org.example.supplier.model.entity.Review;

@Data
@NoArgsConstructor
public class UpdateReviewRequest {

    @Valid
    @NotBlank
    @JsonProperty("message")
    private String message;

    @Valid
    @NotNull
    @JsonProperty("product_id")
    private Long productId;

    public Review toReview(Product product) {
        Review review = new Review();
        review.setMessage(this.message);
        review.setProduct(product);
        return review;
    }
}
