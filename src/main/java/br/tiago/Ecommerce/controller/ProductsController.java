package br.tiago.Ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @GetMapping
    public String displayProducts(){
        return "<h1>Jesus é o meu Deus</h1>";
    }
}
