package com.movie.kit.controller;

import com.movie.kit.api.RestClient;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.constants.MovieUrls;
import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Persons;
import com.movie.kit.domain.Shows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchDataController extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;

    @GetMapping(value = "/search/{searchType}")
    public String shows(@PathVariable String searchType, @RequestParam String searchKey, Model model) {
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchKey", searchKey);
        model.addAttribute("pageIndex", 1);
        model.addAttribute("title", searchType);
        model.addAttribute("viewName", searchType.toUpperCase());
        model.addAttribute("userSearch", searchType);
        return "search/home";
    }


    @GetMapping(value = "/search/append")
    public String showsAppend(@RequestParam String searchType, @RequestParam String searchKey, @RequestParam Integer pageIndex, Model model) {
        return searchType.equalsIgnoreCase(ApiConstants.MOVIES) ? showMovies(searchKey, pageIndex, model) :
                searchType.equalsIgnoreCase(ApiConstants.SHOWS) ? showShows(searchKey, pageIndex, model) :
                        showPersons(searchKey, pageIndex, model);
    }


    public String showShows(@RequestParam String search, @RequestParam Integer pageIndex, Model model) {
        List<Shows> shows = get(hostUrl + MovieUrls.SEARCH_SHOWS_END_POINT + "?search=" + search + "&pageIndex=" + pageIndex, new ParameterizedTypeReference<>() {
        });
        model.addAttribute("shows", shows);
        return pageIndex % 2 == 0 ? "shows/evenAppendShows" : "shows/oddAppendShows";
    }

    public String showMovies(@RequestParam String search, @RequestParam Integer pageIndex, Model model) {
        List<Movies> movies = get(hostUrl + MovieUrls.SEARCH_MOVIES_END_POINT + "?search=" + search + "&pageIndex=" + pageIndex, new ParameterizedTypeReference<>() {
        });
        model.addAttribute("movies", movies);
        return pageIndex % 2 == 0 ? "movies/evenAppendMovies" : "movies/oddAppendMovies";
    }

    public String showPersons(@RequestParam String search, @RequestParam Integer pageIndex, Model model) {
        List<Persons> persons = get(hostUrl + MovieUrls.SEARCH_PERSONS_END_POINT + "?search=" + search + "&pageIndex=" + pageIndex, new ParameterizedTypeReference<>() {
        });
        model.addAttribute("persons", persons);
        model.addAttribute("persons", persons);
        return "persons/appendPersons";
    }
}
