package br.tiago.Ecommerce.controller;

import br.tiago.Ecommerce.model.Person;
import br.tiago.Ecommerce.model.Product;
import br.tiago.Ecommerce.model.ShoppingCart;
import br.tiago.Ecommerce.repository.PersonRepository;
import br.tiago.Ecommerce.repository.ProductRepository;
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

    @GetMapping("")
    public ModelAndView diplayShoppingCart(Authentication authentication){
        ModelAndView modelAndView = new ModelAndView("shopping-cart");
        Person person = personService.findByEmail(authentication.getName());
        modelAndView.addObject("shoppingCart", person.getShoppingCart());
        modelAndView.addObject("locale", Locale.getDefault());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addToCart(@RequestParam int productId, Authentication authentication){
        ModelAndView modelAndView = new ModelAndView("product");
        Product product = productRepository.findById(productId);
        Person person = personService.findByEmail(authentication.getName());
        if(person.getShoppingCart() == null){
            person.setShoppingCart(new ShoppingCart());
            person.getShoppingCart().setDateTime(LocalDateTime.now());
            person.getShoppingCart().setProducts(new ArrayList<>());
        }
        person.getShoppingCart().getProducts().add(product);
        personRepository.save(person);
        modelAndView.addObject("error", "Produto adicionado ao carrinho");
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/remove")
    public ModelAndView removeOfCart(@RequestParam int productId, Authentication authentication){
        ModelAndView modelAndView = new ModelAndView("shopping-cart");
        Person person = personService.findByEmail(authentication.getName());
        person.getShoppingCart().getProducts().remove(productRepository.findById(productId));
        personRepository.save(person);
        return modelAndView;
    }

}
