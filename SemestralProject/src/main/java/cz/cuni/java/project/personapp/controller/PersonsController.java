package cz.cuni.java.project.personapp.controller;

import cz.cuni.java.project.personapp.model.dto.PersonDTO;
import cz.cuni.java.project.personapp.service.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/persons")
public class PersonsController {

    private PersonService personService;

    public PersonsController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String getPersonsPage(
            @RequestParam(value = Parameters.PAGE, required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = Parameters.SIZE, required = false, defaultValue = "${person.app.persons.page.size}") int size,
            Model model
    ) {
        Page<PersonDTO> personDTOPage = personService.getNewPersons(pageNumber - 1, size);
        if (personDTOPage.getTotalPages() > 0) {
            model.addAttribute("pageNumbers", getPageNumbers(personDTOPage));
        }
        model.addAttribute("page", personDTOPage);
        return "persons";
    }

    private List<Integer> getPageNumbers(Page page) {
        return IntStream.rangeClosed(1, page.getTotalPages())
                .boxed()
                .collect(Collectors.toList());
    }
}
