package cz.cuni.java.project.personapp.connector;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PersonProfileAdapter {

    private String profileHost = "http://localhost:8081/profile";

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
}
