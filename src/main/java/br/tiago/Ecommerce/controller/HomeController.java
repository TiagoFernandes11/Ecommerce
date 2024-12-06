package br.tiago.Ecommerce.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @GetMapping(value = {"/", "/home"})
    public ModelAndView displayHome(){
        ModelAndView modelAndView = new ModelAndView("home");
        return  modelAndView;
    }
}
