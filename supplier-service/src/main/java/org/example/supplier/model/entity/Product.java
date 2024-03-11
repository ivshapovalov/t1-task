package org.example.supplier.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(of = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private long id;

    @Valid
    @NotBlank
    @Column(name = "name", nullable = false)
    @JsonProperty("name")
    private String name;

    @Valid
    @NotBlank
    @Column(name = "description", nullable = false)
    @JsonProperty("description")
    private String description;

    @Valid
    @NotNull
    @Column(name = "price", nullable = false)
    @JsonProperty("price")
    private BigDecimal price;

    @Valid
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonManagedReference
    @JsonProperty("category_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Category category;

    @Valid
    @OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonProperty("review_ids")
    @JsonIdentityReference(alwaysAsId = true)
//    @Fetch(FetchMode.JOIN)
    private Set<Review> reviews;

}
