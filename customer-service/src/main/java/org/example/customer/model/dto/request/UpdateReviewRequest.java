package org.example.customer.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewRequest {

    @Valid
    @NotBlank
    @JsonProperty("message")
    private String message;

    @Valid
    @NotNull
    @JsonProperty("product_id")
    private Long productId;
}
