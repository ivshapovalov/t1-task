package org.example.supplier.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.supplier.model.entity.Category;
import org.example.supplier.model.entity.Product;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
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

    public Product toProduct(Category category) {
        Product product = new Product();
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setCategory(category);
        return product;
    }

}
