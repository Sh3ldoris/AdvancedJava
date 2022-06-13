package cz.cuni.java.project.profilegeneratingapplication.controller;

import cz.cuni.java.project.profilegeneratingapplication.service.ProfileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;

@Controller
public class TestController {

    private ProfileService profileService;

    public TestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping(value = "/hej", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public InputStreamResource pdf() {
        return new InputStreamResource(new ByteArrayInputStream(profileService.getPdfPersonProfile(1l)));
    }
}
