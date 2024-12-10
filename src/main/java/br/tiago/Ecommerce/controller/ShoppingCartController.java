package br.tiago.Ecommerce.controller;

import br.tiago.Ecommerce.model.CartItem;
import br.tiago.Ecommerce.model.Person;
import br.tiago.Ecommerce.model.Product;
import br.tiago.Ecommerce.model.ShoppingCart;
import br.tiago.Ecommerce.repository.CartItemRepository;
import br.tiago.Ecommerce.repository.PersonRepository;
import br.tiago.Ecommerce.repository.ProductRepository;
import br.tiago.Ecommerce.repository.ShoppingCartRepository;
import br.tiago.Ecommerce.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    PersonService personService;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @GetMapping("")
    public ModelAndView diplayShoppingCart(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView("shopping-cart");
        Person person = personService.findByEmail(authentication.getName());
        modelAndView.addObject("shoppingCart", person.getShoppingCart());
        modelAndView.addObject("locale", Locale.getDefault());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addToCart(@RequestParam int productId, Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cart");
        Person person = personRepository.findByEmail(authentication.getName());
        Product product = productRepository.findById(productId);
        CartItem cartItem = new CartItem(product);
        if(person.getShoppingCart() == null){
            person.setShoppingCart(new ShoppingCart());
            person.getShoppingCart().setDateTime(LocalDateTime.now());
            person.getShoppingCart().setCartItems(new ArrayList<CartItem>());
        }
        if(person.getShoppingCart().hasProduct(product)){
            person.getShoppingCart().getCartItemByProduct(product).increaseQuantity();
        } else {
            person.getShoppingCart().getCartItems().add(cartItem);
        }
        personRepository.save(person);
        return modelAndView;
    }


    @PostMapping("/remove")
    public ModelAndView removeOfCart(@RequestParam int productId, Authentication authentication){
        ModelAndView modelAndView = new ModelAndView("redirect:/cart");
        Person person = personService.findByEmail(authentication.getName());
        Product product = productRepository.findById(productId);
        CartItem cartItem = cartItemRepository.findByProduct(product);
        person.getShoppingCart().getCartItems().remove(cartItem);
        shoppingCartRepository.save(person.getShoppingCart());
        personRepository.save(person);
        return modelAndView;
    }

}
