package cz.cuni.java.project.personapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminHomeController {

    @GetMapping
    public String getAdminHomePage() {
        return "admin-home";
    }
}
