package cz.cuni.java.project.profilegeneratingapplication.connector;

import cz.cuni.java.project.profilegeneratingapplication.service.ProfileService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PersonProfileAdapter {

    private ProfileService profileService;

    public PersonProfileAdapter(ProfileService profileService) {
        this.profileService = profileService;
    }


    @RabbitListener( queues = "${person.app.persons.generate.profile.source}")
    public void generateProfile(Message message) {
        String personXml = new String(message.getBody());
        Long personId = message.getMessageProperties().getHeader("personId");
        profileService.generateAndSavePersonProfile(personId, personXml);
    }
}
