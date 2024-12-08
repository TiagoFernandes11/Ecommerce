package br.tiago.Ecommerce.controller;

import br.tiago.Ecommerce.model.Product;
import br.tiago.Ecommerce.repository.ProductRepository;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/register")
    public ModelAndView displayRegisterProduct(){
        ModelAndView modelAndView = new ModelAndView("add-product");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registeProduct(@Valid Product product, Errors errors){
        ModelAndView modelAndView = new ModelAndView("add-product");
        if(errors.hasErrors()){
            modelAndView.addObject("error", errors.toString());
            return modelAndView;
        }
        productRepository.save(product);
        modelAndView.addObject("error", "Produto cadastrado com sucesso");
        return modelAndView;
    }

    @GetMapping("/view-item/{id}")
    public ModelAndView displayProduct(@PathVariable int id){
        ModelAndView modelAndView = new ModelAndView("product");
        Product product = productRepository.findById(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }
}
