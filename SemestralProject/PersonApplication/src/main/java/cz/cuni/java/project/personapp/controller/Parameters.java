package cz.cuni.java.project.personapp.controller;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Parameters {

    public static final String PERSON_DTO = "person";
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    public static final String ID = "id";

    public Parameters() {
    }

    public static List<Integer> getPageNumbers(Page page) {
        return IntStream.rangeClosed(1, page.getTotalPages())
                .boxed()
                .collect(Collectors.toList());
    }
}
