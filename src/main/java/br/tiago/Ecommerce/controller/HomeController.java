package br.tiago.Ecommerce.controller;

import br.tiago.Ecommerce.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    PersonRepository personRepository;

    @GetMapping(value = {"/", "/home"})
    public ModelAndView displayHome(){
        return new ModelAndView("home");
    }
}
