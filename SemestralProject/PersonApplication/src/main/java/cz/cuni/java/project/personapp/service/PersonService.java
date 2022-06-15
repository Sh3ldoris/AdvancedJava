package cz.cuni.java.project.personapp.service;

import cz.cuni.java.project.personapp.exception.PersonException;
import cz.cuni.java.project.personapp.model.Person;
import cz.cuni.java.project.personapp.model.dto.PersonDTO;
import cz.cuni.java.project.personapp.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private static final Logger LOGGER = LogManager.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void savePerson(PersonDTO person) {
        String firstName = person.firstName();
        if (firstName == null || firstName.equals("")) {
            String message = "Persons first name cannot be null or empty String!";
            LOGGER.warn(message);
            throw new PersonException(message);
        }

        String lastName = person.lastName();
        if (lastName == null || lastName.equals("")) {
            String message = "Persons lastname name cannot be null or empty String!";
            LOGGER.warn(message);
            throw new PersonException(message);
        }

        String address = person.address();
        if (address == null || address.equals("")) {
            String message = "Persons address name cannot be null or empty String!";
            LOGGER.warn(message);
            throw new PersonException(message);
        }

        String city = person.city();
        if (city == null || city.equals("")) {
            String message = "Persons city name cannot be null or empty String!";
            LOGGER.warn(message);
            throw new PersonException(message);
        }

        personRepository.save(
                new Person(firstName, lastName, address, city, person.occupation())
        );
    }

    public Page<PersonDTO> getNewPersonsPaged(int page, int size) {
        Pageable personsPage = PageRequest.of(page, size);
        Page<Person> personPage = personRepository.findAllByIsProfileGenerated(false, personsPage);
        List<PersonDTO> personDTOs = personPage.getContent().stream().map(this::personToPersonDTO).toList();
        return new PageImpl<>(personDTOs, personPage.getPageable(), personPage.getTotalElements());
    }

    public List<Person> getNewPersons() {
        return personRepository.findAll().stream()
                .filter(person -> !person.isProfileGenerated())
                .toList();
    }

    public Page<PersonDTO> getPersonsWithProfiles(int page, int size) {
        Pageable personsPage = PageRequest.of(page, size);
        Page<Person> personPage = personRepository.findAllByIsProfileGenerated(true, personsPage);
        List<PersonDTO> personDTOs = personPage.getContent().stream().map(this::personToPersonDTO).toList();
        return new PageImpl<>(personDTOs, personPage.getPageable(), personPage.getTotalElements());
    }

    public void updatePerson(Person person) {
        if (person.getId() != null && personRepository.existsById(person.getId())) {
            personRepository.save(person);
        } else {
            String message = "Cannot find Person by given id -> " + person;
            LOGGER.warn(message);
            throw new PersonException(message);
        }
    }

    private PersonDTO personToPersonDTO(Person person) {
        return new PersonDTO(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getCity(),
                person.getOccupation()
        );
    }
}
