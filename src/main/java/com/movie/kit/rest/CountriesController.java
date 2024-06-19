package com.movie.kit.rest;

import com.movie.kit.autowired.AutowiredMappedService;
import com.movie.kit.domain.Countries;
import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Shows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CountriesController extends AutowiredMappedService {

    @GetMapping(value = "/countries")
    public List<Countries> getCountries() {
        return getCountryService().getAllCountries();
    }

    @GetMapping(value = "/country/movies")
    public List<Movies> countryMovies(@RequestParam String countryCode, @RequestParam String pageIndex) {
        return getCountryService().getCountryMoviesByCountryCodeAndPageIndex(countryCode, pageIndex);
    }

    @GetMapping(value = "/country/shows")
    public List<Shows> countryShows(@RequestParam String countryCode, @RequestParam String pageIndex) {
        return getCountryService().getCountryShowsByCountryCodeAndPageIndex(countryCode, pageIndex);
    }
}

