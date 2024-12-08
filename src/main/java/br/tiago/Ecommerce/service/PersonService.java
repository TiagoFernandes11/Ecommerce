package br.tiago.Ecommerce.service;

import br.tiago.Ecommerce.constants.EcommerceConstants;
import br.tiago.Ecommerce.model.Person;
import br.tiago.Ecommerce.model.ShoppingCart;
import br.tiago.Ecommerce.repository.PersonRepository;
import br.tiago.Ecommerce.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean save(Person person){
        boolean isSaved = false;
        Person personEntity = personRepository.findByEmail(person.getEmail());
        if(personEntity == null){
            person.setEmail(person.getEmail().toLowerCase());
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            person.setRole(roleRepository.findByName(EcommerceConstants.USER));
            personRepository.save(person);
            isSaved = true;
        }
        return isSaved;
    }

    public void updateRegister(String name, String email, Authentication authentication){
        Person person = personRepository.findByEmail(authentication.getName());
        person.setName(name);
        person.setEmail(email);
        personRepository.save(person);
    }

    public boolean updatePassword(String oldPassword, String newPassword, String confirmNewPassword, Authentication authentication){
        boolean isSaved = false;
        Person person = personRepository.findByEmail(authentication.getName());
        if(passwordEncoder.matches(oldPassword, person.getPassword()) && newPassword.equals(confirmNewPassword)){
            person.setPassword(passwordEncoder.encode(newPassword));
            personRepository.save(person);
            isSaved = true;
        }
        return isSaved;
    }

    public Person findByEmail(String email){
        return personRepository.findByEmail(email);
    }

    public ShoppingCart getShoppingCart(Person person){
        return person.getShoppingCart();
    }
}
