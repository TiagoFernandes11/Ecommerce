package br.tiago.Ecommerce.controller;

import br.tiago.Ecommerce.model.Person;
import br.tiago.Ecommerce.service.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
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
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout){
        ModelAndView modelAndView = new ModelAndView("login");
        if(error != null){
            modelAndView.addObject("error", "Credenciais invalidas");
            return modelAndView;
        }
        if(logout != null){
            modelAndView.addObject("error", "Logout efetuado com sucesso");
            return modelAndView;
        }
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/person/login?logout=true";
    }

    @GetMapping("/update/register")
    public ModelAndView displayUpdateRegister(){
        return new ModelAndView("update-user");
    }

    @PostMapping("/update/register")
    public ModelAndView updateRegister(@RequestParam String name, @RequestParam String email, Authentication authentication){
        ModelAndView modelAndView = new ModelAndView("update-user");
        personService.updateRegister(name, email, authentication);
        modelAndView.addObject("error", "cadastro alterado com sucesso");
        return modelAndView;
    }

    @GetMapping("/update/password")
    public ModelAndView displayUpdatePassword(){
        return new ModelAndView("update-password");
    }

    @PostMapping("/update/password")
    public ModelAndView updatePassword(@RequestParam String oldPassword,
                                       @RequestParam String newPassword,
                                       @RequestParam String confirmNewPassword,
                                       Authentication authentication){
        ModelAndView modelAndView = new ModelAndView("update-password");
        if(personService.updatePassword(oldPassword, newPassword, confirmNewPassword, authentication)){
            modelAndView.addObject("error", "A senha foi atualizada com sucesso");
        }
        else {
            modelAndView.addObject("error", "Senha antiga é invalida ou as novas senhas não são iguais");
        }
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView displayProfile(Authentication authentication){
        Person person = personService.findByEmail(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("person", person);
        return modelAndView;
    }

}
