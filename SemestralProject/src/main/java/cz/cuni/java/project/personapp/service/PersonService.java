package cz.cuni.java.project.personapp.service;

import cz.cuni.java.project.personapp.model.Person;
import cz.cuni.java.project.personapp.model.dto.PersonDTO;
import cz.cuni.java.project.personapp.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    public void savePerson(PersonDTO person) {
        String firstName = person.firstName();
        if (firstName == null || firstName == null) {
            //TODO: throw ex
        }

        String lastName = person.lastName();
        if (lastName == null || lastName == null) {
            //TODO: throw ex
        }

        String address = person.address();
        if (address == null || address == null) {
            //TODO: throw ex
        }

        String city = person.city();
        if (city == null || city == null) {
            //TODO: throw ex
        }

        personRepository.save(
                new Person(firstName, lastName, address, city, person.occupation())
        );
    }
}
