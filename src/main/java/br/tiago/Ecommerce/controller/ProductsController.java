package br.tiago.Ecommerce.controller;

import br.tiago.Ecommerce.model.Product;
import br.tiago.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductsController {

    @Autowired
    ProductService productService;

    @GetMapping("/products")
    public ModelAndView displayProducts(@RequestParam int pageNum,
                                        @RequestParam String sortField,
                                        @RequestParam String sortDir){
        ModelAndView modelAndView = new ModelAndView("products");
        Page<Product> productPage = productService.findAllProducts(pageNum, sortField, sortDir);
        List<Product> products = productPage.getContent();
        modelAndView.addObject("products", products);
        modelAndView.addObject("page", pageNum);
        modelAndView.addObject("totalPages", productPage.getTotalPages());
        modelAndView.addObject("pageNum", pageNum);
        modelAndView.addObject("sortField", sortField);
        modelAndView.addObject("sortDir", sortDir);
        return modelAndView;
    }
}
