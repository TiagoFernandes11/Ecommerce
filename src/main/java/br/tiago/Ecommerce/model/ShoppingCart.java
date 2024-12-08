package br.tiago.Ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Past
    private LocalDateTime dateTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, targetEntity = Product.class)
    @JoinColumn(name = "products_id", referencedColumnName = "id", nullable = true)
    private List<Product> products;
}
