package cz.cuni.java.project.personapp.controller;

import cz.cuni.java.project.personapp.exception.PersonException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler({PersonException.class})
    public String userNotFoundException(Exception ex) {
        return "error";
    }
}
