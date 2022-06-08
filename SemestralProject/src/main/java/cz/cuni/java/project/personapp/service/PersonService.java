package cz.cuni.java.project.personapp.service;

import cz.cuni.java.project.personapp.exception.PersonException;
import cz.cuni.java.project.personapp.model.Person;
import cz.cuni.java.project.personapp.model.dto.PersonDTO;
import cz.cuni.java.project.personapp.repository.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    public void savePerson(PersonDTO person) {
        String firstName = person.firstName();
        if (firstName == null || firstName.equals("")) {
            throw new PersonException("Persons first name cannot be null or empty String!");
        }

        String lastName = person.lastName();
        if (lastName == null || lastName.equals("")) {
            throw new PersonException("Persons lastname name cannot be null or empty String!");
        }

        String address = person.address();
        if (address == null || address.equals("")) {
            throw new PersonException("Persons address name cannot be null or empty String!");
        }

        String city = person.city();
        if (city == null || city.equals("")) {
            throw new PersonException("Persons city name cannot be null or empty String!");
        }

        personRepository.save(
                new Person(firstName, lastName, address, city, person.occupation())
        );
    }

    public Page<PersonDTO> getNewPersons(int page, int size) {
        Pageable personsPage = PageRequest.of(page, size);
        Page<Person> personPage = personRepository.findAllByIsProfileGenerated(false, personsPage);
        List<PersonDTO> personDTOs = personPage.getContent().stream().map(this::personToPersonDTO).toList();
        return new PageImpl<>(personDTOs, personPage.getPageable(), personPage.getTotalElements());
    }

    private PersonDTO personToPersonDTO(Person person) {
        return new PersonDTO(
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getCity(),
                person.getOccupation()
        );
    }
}
