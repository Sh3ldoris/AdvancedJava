package cz.cuni.java.project.personapp.controller;

import cz.cuni.java.project.personapp.connector.PersonProfileAdapter;
import cz.cuni.java.project.personapp.model.dto.PersonDTO;
import cz.cuni.java.project.personapp.service.PersonService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;

@Controller
@RequestMapping("/profiles")
public class ProfilesController {

    private PersonService personService;
    private PersonProfileAdapter personProfileAdapter;

    public ProfilesController(PersonService personService, PersonProfileAdapter personProfileAdapter) {
        this.personService = personService;
        this.personProfileAdapter = personProfileAdapter;
    }

    @GetMapping
    public String getProfilesPage(
            @RequestParam(value = Parameters.PAGE, required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = Parameters.SIZE, required = false, defaultValue = "${person.app.persons.page.size}") int size,
            Model model
    ) {
        Page<PersonDTO> personDTOPage = personService.getPersonsWithProfiles(pageNumber - 1, size);
        if (personDTOPage.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", Parameters.getPageNumbers(personDTOPage));
        }
        model.addAttribute("page", personDTOPage);
        return "profiles";
    }

    @GetMapping(value = "html", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getHtmlPeronProfile(
            @RequestParam(value = Parameters.ID, required = false, defaultValue = "1") Long personId
    ) {
        return personProfileAdapter.getPersonHtmlProfile(personId);
    }

    @GetMapping(value = "pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public InputStreamResource getPdfPeronProfile(
            @RequestParam(value = Parameters.ID, required = false, defaultValue = "1") Long personId
    ) {
        return new InputStreamResource(new ByteArrayInputStream(personProfileAdapter.getPersonPdfProfile(personId)));
    }
}
