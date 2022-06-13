package cz.cuni.java.project.personapp.service;

import cz.cuni.java.project.personapp.exception.PersonException;
import cz.cuni.java.project.personapp.model.Person;
import cz.cuni.java.project.personapp.model.dto.PersonDTO;
import cz.cuni.java.project.personapp.repository.PersonRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private AmqpTemplate rabbitmqTemplate;

    @Value("${person.app.persons.generate.profile.dest:destination}")
    private String generateProfileDest;

    public PersonService(PersonRepository personRepository, AmqpTemplate rabbitmqTemplate) {

        this.personRepository = personRepository;
        this.rabbitmqTemplate = rabbitmqTemplate;
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

    public void updatePerson(Person person) {
        if (person.getId() != null && personRepository.existsById(person.getId())) {
            personRepository.save(person);
        } else {
            throw new PersonException("Cannot find Person by given id -> " + person);
        }
    }

    public void generateProfiles(Long id) {
        Person person = personRepository.findById(id).orElse(null);

        if (person == null) {
            throw new PersonException("Cannot find Person by given id -> " + id);
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(person.getClass());
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(person, sw);

            MessageProperties properties = new MessageProperties();
            properties.setHeader("personId", person.getId());

            MessageBuilder messageBuilder = MessageBuilder
                    .withBody(sw.toString().getBytes())
                    .andProperties(properties);
            rabbitmqTemplate.send(generateProfileDest, messageBuilder.build());

//            person.setProfileGenerated(true);
//            updatePerson(person);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
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
