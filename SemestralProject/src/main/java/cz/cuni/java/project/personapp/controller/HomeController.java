package cz.cuni.java.project.personapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToIndex() {
        return "index";
    }

    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }
}
