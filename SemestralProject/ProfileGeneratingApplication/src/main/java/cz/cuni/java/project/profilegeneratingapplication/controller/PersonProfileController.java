package cz.cuni.java.project.profilegeneratingapplication.controller;

import cz.cuni.java.project.profilegeneratingapplication.service.ProfileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class PersonProfileController {

    private final ProfileService profileService;

    public PersonProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("html")
    public ResponseEntity<String> getHtmlPersonProfile(@RequestParam(value = "id") Long personId) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(profileService.getHtmlPersonProfile(personId));
    }

    @GetMapping("pdf")
    public ResponseEntity<byte[]> getPdfPersonProfile(@RequestParam(value = "id") Long personId) {
        return ResponseEntity
                .ok()
                .body(profileService.getPdfPersonProfile(personId));
    }
}

