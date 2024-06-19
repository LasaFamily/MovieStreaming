package com.movie.kit.controller;

import com.movie.kit.api.RestClient;
import com.movie.kit.constants.MovieUrls;
import com.movie.kit.domain.Countries;
import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Shows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CountryController extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;

    @GetMapping(value = "/countries")
    public String genres(Model model) {
        List<Countries> countries = get(hostUrl + MovieUrls.COUNTRIES_END_POINT, new ParameterizedTypeReference<>() {
        });
        System.out.println(countries.size());
        model.addAttribute("countries", getTopCountries(countries));
        return "country/countries";
    }

    @GetMapping(value = "/country/movies/{countryCode}")
    public String movies(@PathVariable String countryCode, Model model) {
        model.addAttribute("countryCode", countryCode);
        model.addAttribute("pageIndex", 1);
        model.addAttribute("title", countryCode + "-movies");
        model.addAttribute("userSearch", "movies");
        return "country/movies";
    }

    @GetMapping(value = "/country/shows/{countryCode}")
    public String shows(@PathVariable String countryCode, Model model) {
        model.addAttribute("countryCode", countryCode);
        model.addAttribute("pageIndex", 1);
        model.addAttribute("title", countryCode + "-shows");
        model.addAttribute("userSearch", "shows");
        return "country/shows";
    }

    @GetMapping(value = "/country/movies/append")
    public String moviesAppend(@RequestParam String countryCode, @RequestParam Integer pageIndex, Model model) {
        List<Movies> movies = get(hostUrl + MovieUrls.COUNTRY_MOVIES_END_POINT + "?countryCode=" + countryCode +
                "&pageIndex=" + pageIndex, new ParameterizedTypeReference<>() {});
        model.addAttribute("countryCode", countryCode);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("movies", movies);
        return pageIndex % 2 == 0 ? "movies/evenAppendMovies" : "movies/oddAppendMovies";
    }

    @GetMapping(value = "/country/shows/append")
    public String showsAppend(@RequestParam String countryCode, @RequestParam Integer pageIndex, Model model) {
        List<Shows> shows = get(hostUrl + MovieUrls.COUNTRY_SHOWS_END_POINT + "?countryCode=" + countryCode +
                "&pageIndex=" + pageIndex, new ParameterizedTypeReference<>() {});
        model.addAttribute("countryCode", countryCode);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("shows", shows);
        return pageIndex % 2 == 0 ? "shows/evenAppendShows" : "shows/oddAppendShows";
    }


    private Map<Integer, List<Countries>> getTopCountries(List<Countries> topCountries) {
        final Integer[] index = {1};
        Map<Integer, List<Countries>> countries = topCountries.stream().map(country -> {
            if(index[0] <=64) {
                country.setCountryIndex(1);
            } else if(index[0] <=128) {
                country.setCountryIndex(2);
            } else if(index[0] <=192) {
                country.setCountryIndex(3);
            } else if(index[0] <=251) {
                country.setCountryIndex(4);
            }
            country.setCountryNumber(index[0] < 10 ? ("0" + index[0]) : index[0].toString());
            index[0]++;
            return country;
        }).collect(Collectors.groupingBy(Countries::getCountryIndex, Collectors.toList()));
        return countries;
    }
}
