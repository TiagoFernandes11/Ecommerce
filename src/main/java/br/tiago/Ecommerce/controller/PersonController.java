package br.tiago.Ecommerce.controller;

import br.tiago.Ecommerce.model.Person;
import br.tiago.Ecommerce.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @GetMapping("/register")
    public ModelAndView displayRegisterForm(){
        ModelAndView modelAndView = new ModelAndView("register.html");
        modelAndView.addObject("person", new Person());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid Person person, Errors errors){
        ModelAndView modelAndView = new ModelAndView("register");
        if(errors.hasErrors()){
            return modelAndView;
        }
        if(!person.getPassword().equals(person.getConfirmPassword())){
            modelAndView.addObject("error", "As senhas não são iguais");
            return modelAndView;
        }
        if(personService.save(person)){
            modelAndView.addObject("error", "Cadastro efetuado com sucesso");
            return modelAndView;
        }
        modelAndView.addObject("error", "Email ja está cadastrado");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView displayLogin(){
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String error){
        ModelAndView modelAndView = new ModelAndView("login");
        if(error != null){
            modelAndView.addObject("error", "Credenciais invalidas");
            return modelAndView;
        }
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
