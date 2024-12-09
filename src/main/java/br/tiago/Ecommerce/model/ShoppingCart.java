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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    private List<Product> products;

    public double total(){
        double total = 0.0;
        for(Product product : this.products){
            total += product.getPrice();
        }
        return total;
    }
}
