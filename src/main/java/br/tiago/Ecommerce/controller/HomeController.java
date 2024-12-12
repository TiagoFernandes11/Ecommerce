package br.tiago.Ecommerce.controller;

import br.tiago.Ecommerce.model.Product;
import br.tiago.Ecommerce.repository.PersonRepository;
import br.tiago.Ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping(value = {"/", "/home"})
    public ModelAndView displayHome(){
        ModelAndView modelAndView = new ModelAndView("home");
        List<Product> products = productRepository.findAll();
        List<Product> fiveproducts = products.subList(0,4);
        if(!products.isEmpty()){
            modelAndView.addObject("products", fiveproducts);
        }
        return modelAndView;
    }
}
