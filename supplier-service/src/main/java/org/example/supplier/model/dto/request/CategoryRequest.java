package org.example.supplier.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.supplier.model.entity.Category;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {

    @Valid
    @NotBlank
    @JsonProperty("name")
    private String name;

    public Category toCategory() {
        Category category = new Category();
        category.setName(this.name);
        return category;
    }

}
