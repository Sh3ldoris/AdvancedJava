package cz.cuni.java.project.profilegeneratingapplication.model;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Base64;

@Entity
public class UserProfile {

    @Id
    private Long id;
    @Lob
    @NonNull
    private String htmlProfile;

    public UserProfile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHtmlProfile() {
        return htmlProfile;
    }

    public String getHtmlProfileDecoded() {
        byte[] htmlBytes = Base64.getDecoder().decode(htmlProfile);
        return new String(htmlBytes);
    }

    public void setHtmlProfile(String htmlProfile) {
        this.htmlProfile = htmlProfile;
    }
}
