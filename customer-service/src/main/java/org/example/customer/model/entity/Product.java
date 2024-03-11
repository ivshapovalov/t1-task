package org.example.customer.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@EqualsAndHashCode(of = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NoArgsConstructor
public class Product {

    @JsonProperty("id")
    private long id;

    @Valid
    @NotBlank
    @JsonProperty("name")
    private String name;

    @Valid
    @NotBlank
    @JsonProperty("description")
    private String description;

    @Valid
    @NotNull
    @JsonProperty("price")
    private BigDecimal price;

    @Valid
    @JsonProperty("category_id")
    private Long categoryId;

    @Valid
    @JsonProperty("review_ids")
    private Set<Long> reviewIds;

}
