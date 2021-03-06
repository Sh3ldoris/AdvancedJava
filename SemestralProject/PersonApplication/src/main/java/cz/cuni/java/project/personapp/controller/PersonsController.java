package cz.cuni.java.project.personapp.controller;

import cz.cuni.java.project.personapp.connector.PersonProfileAdapter;
import cz.cuni.java.project.personapp.model.dto.PersonDTO;
import cz.cuni.java.project.personapp.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/persons")
public class PersonsController {

    private PersonService personService;
    private PersonProfileAdapter personProfileAdapter;

    public PersonsController(PersonService personService, PersonProfileAdapter personProfileAdapter) {
        this.personService = personService;
        this.personProfileAdapter = personProfileAdapter;
    }

    @GetMapping
    public String getPersonsPage(
            @RequestParam(value = Parameters.PAGE, required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = Parameters.SIZE, required = false, defaultValue = "${person.app.persons.page.size}") int size,
            Model model
    ) {
        Page<PersonDTO> personDTOPage = personService.getNewPersonsPaged(pageNumber - 1, size);
        if (personDTOPage.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", Parameters.getPageNumbers(personDTOPage));
        }
        model.addAttribute("page", personDTOPage);
        return "persons";
    }

    @PostMapping
    public String generateProfiles(Model model) {
        personService.getNewPersons()
                .forEach(person -> {
                    personProfileAdapter.generateProfile(person);
                    person.setProfileGenerated(true);
                    personService.updatePerson(person);
                });
        model.addAttribute("message", "user profiles sent for generating");
        return "user-message";
    }
}
