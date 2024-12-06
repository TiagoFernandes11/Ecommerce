package br.tiago.Ecommerce.service;

import br.tiago.Ecommerce.model.Person;
import br.tiago.Ecommerce.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
