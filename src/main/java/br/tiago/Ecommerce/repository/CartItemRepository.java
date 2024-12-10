package br.tiago.Ecommerce.repository;

import br.tiago.Ecommerce.model.CartItem;
import br.tiago.Ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    CartItem findByProduct(Product product);
}
