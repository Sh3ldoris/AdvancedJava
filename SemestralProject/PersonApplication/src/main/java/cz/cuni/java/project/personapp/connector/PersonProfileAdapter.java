package cz.cuni.java.project.personapp.connector;

import cz.cuni.java.project.personapp.exception.PersonException;
import cz.cuni.java.project.personapp.model.Person;
import cz.cuni.java.project.personapp.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Component
public class PersonProfileAdapter {

    private static final Logger LOGGER = LogManager.getLogger(PersonProfileAdapter.class);

    private AmqpTemplate rabbitmqTemplate;
    @Value("profile.service.rest.url")
    private String profileHost;
    @Value("${person.app.persons.generate.profile.dest:destination}")
    private String generateProfileDest;

    public PersonProfileAdapter(AmqpTemplate rabbitmqTemplate) {
        this.rabbitmqTemplate = rabbitmqTemplate;
    }

    public String getPersonHtmlProfile(Long personId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(profileHost + "/html?id=" + personId, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return "";
        }
    }


    public byte[] getPersonPdfProfile(Long personId) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.getForEntity(profileHost + "/pdf?id=" + personId, byte[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return new byte[] {};
        }
    }

    public void generateProfile(Person person) {
        if (person == null) {
            String message = "Cannot find Person";
            LOGGER.warn(message);
            throw new PersonException(message);
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

            person.setProfileGenerated(true);
        } catch (JAXBException e) {
            LOGGER.warn("Error while creating profile!");
            e.printStackTrace();
        }
    }
}
