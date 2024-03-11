package org.example.customer.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@EqualsAndHashCode(of = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @JsonProperty("id")
    private long id;

    @Valid
    @NotBlank
    @JsonProperty("name")
    private String name;

    public Category(String name) {
        this.name = name;
    }

}
