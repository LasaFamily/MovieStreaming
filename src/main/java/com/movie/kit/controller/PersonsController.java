package com.movie.kit.controller;

import com.movie.kit.api.RestClient;
import com.movie.kit.constants.MovieUrls;
import com.movie.kit.domain.Persons;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PersonsController extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;

    @GetMapping(value = "/persons")
    public String shows(Model model) {
        model.addAttribute("pageIndex", 1);
        model.addAttribute("title", "persons");
        model.addAttribute("viewName", "Persons");
        model.addAttribute("userSearch", "persons");
        return "persons/persons";
    }

    @GetMapping(value = "/persons/append")
    public String showsAppend(@RequestParam Integer pageIndex, Model model) {
        List<Persons> persons = get(hostUrl + MovieUrls.PERSONS_END_POINT + "?pageIndex=" + pageIndex, new ParameterizedTypeReference<>() {});
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("persons", persons);
        return "persons/appendPersons";
    }
}
