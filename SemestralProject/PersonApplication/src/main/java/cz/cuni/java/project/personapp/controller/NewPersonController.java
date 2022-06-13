package cz.cuni.java.project.personapp.controller;

import cz.cuni.java.project.personapp.model.dto.PersonDTO;
import cz.cuni.java.project.personapp.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/person/new")
public class NewPersonController {

    private PersonService personService;

    public NewPersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String getNewPersonPage(Model model) {
        model.addAttribute("person", new PersonDTO(null, "", "", "", "", ""));
        return "new-person";
    }

    @PostMapping
    public String savePerson(
            @ModelAttribute(value=Parameters.PERSON_DTO) PersonDTO personDTO,
            Model model
    ) {
        if (personDTO != null)
            personService.savePerson(personDTO);
        return "/index";
    }
}
