package org.example.supplier.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "products")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private long id;

    @Valid
    @NotBlank
    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @Valid
    @OneToMany(mappedBy = "category", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonBackReference
    @JsonIgnore
    private Set<Product> products;

    public Category(String name) {
        this.name = name;
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
