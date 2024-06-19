package com.movie.kit.controller;

import com.movie.kit.api.RestClient;
import com.movie.kit.constants.MovieUrls;
import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Persons;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PersonMoviesController extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;

    @GetMapping(value = "/person/movies/{personId}")
    public String personMovies(@PathVariable String personId, Model model) {
        Persons person = get(hostUrl + MovieUrls.PERSON_DETAILS_END_POINT + "?personId=" +
                personId, new ParameterizedTypeReference<>() {});

        Map<String, List<Movies>> movies = get(hostUrl + MovieUrls.PERSON_MOVIES_END_POINT + "?personId=" +
                personId, new ParameterizedTypeReference<>() {});

        int moviesCount = getTotalCount(movies).size() / 20;
        model.addAttribute("moviesCount", moviesCount + 1);
        model.addAttribute("pageIndex", 1);
        model.addAttribute("personId", personId);
        model.addAttribute("person", person);
        model.addAttribute("title", "person Movies");
        model.addAttribute("viewName", "Person Movies");
        model.addAttribute("userSearch", "movies");
        return "persons/movies";
    }


    @GetMapping(value = "/person/movies/append")
    public String personMoviesAppend(@RequestParam String personId, @RequestParam Integer pageIndex, Model model) {
        Map<String, List<Movies>> movieMap = get(hostUrl + MovieUrls.PERSON_MOVIES_END_POINT + "?personId=" +
                personId, new ParameterizedTypeReference<>() {});
        List<Movies> allMovies =  getTotalCount(movieMap);
        int startIndex = pageIndex * 20 - 20;
        int endIndex = pageIndex * 20;

        List<Movies> movies = IntStream.range(startIndex, Math.min(allMovies.size(), endIndex))
                        .mapToObj(allMovies::get)
                        .collect(Collectors.toList());
        model.addAttribute("movies", movies);
        return pageIndex % 2 == 0 ? "movies/evenAppendMovies" : "movies/oddAppendMovies";
    }

    private List<Movies> getTotalCount(Map<String, List<Movies>> movies) {
        return movies.values().stream().flatMap(Collection::stream).distinct().collect(Collectors.toList());
    }
}
