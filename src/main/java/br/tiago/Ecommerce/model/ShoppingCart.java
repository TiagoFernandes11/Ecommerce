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
            name = "cart_item_product",
            joinColumns = @JoinColumn(name = "cart_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cart_items_id", referencedColumnName = "id")
    )
    private List<CartItem> cartItems;

    public boolean hasProduct(Product product){
        for(CartItem cartItem : cartItems){
            if(cartItem.getProduct() == product){
                return true;
            }
        }
        return false;
    }

    public CartItem getCartItemByProduct(Product product){
        for(CartItem cartItem : cartItems){
            if(cartItem.getProduct() == product)
                return cartItem;
        }
        return null;
    }

    public double total(){
        double total = 0.0;
        for(CartItem cartItem : this.cartItems){
            total += (cartItem.getProduct().getPrice() * cartItem.getQuantity());
        }
        return total;
    }
}
