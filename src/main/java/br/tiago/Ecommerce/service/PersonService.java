package br.tiago.Ecommerce.service;

import br.tiago.Ecommerce.model.Person;
import br.tiago.Ecommerce.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean save(Person person){
        boolean isSaved = false;
        Person personEntity = personRepository.findByEmail(person.getEmail());
        if(personEntity == null){
            person.setPassword(passwordEncoder.encode(person.getPassword()));
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
}
