package org.example.customer.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @Valid
    @NotBlank
    @JsonProperty("name")
    private String name;

    @Valid
    @NotBlank
    @JsonProperty("description")
    private String description;

    @Valid
    @Min(0)
    @NotNull
    @JsonProperty("price")
    private BigDecimal price;

    @Valid
    @Min(0)
    @JsonProperty("category_id")
    private Long categoryId;

}
