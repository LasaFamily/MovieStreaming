package com.movie.kit.rest;

import com.movie.kit.autowired.AutowiredMappedService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Persons;
import com.movie.kit.domain.Shows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class PersonController extends AutowiredMappedService {

    @GetMapping(value = "/persons")
    public List<Persons> persons(@RequestParam String pageIndex) {
        return getPersonService().getPersonsByPageIndex(pageIndex);
    }

    @GetMapping(value = "/person/details")
    public Persons personDetails(@RequestParam String personId) {
        return getPersonService().getPersonDetailsByPersonId(personId);
    }

    @GetMapping(value = "/person/all/movies")
    public List<Movies> personAllMovies(@RequestParam String personId) {
        Map<String, List<Movies>> moviesMap = getPersonService().getPersonMoviesByPersonId(personId);
        List<Movies> castMovies = moviesMap.get(ApiConstants.CAST.toLowerCase());
        List<Movies> crewMovies = moviesMap.get(ApiConstants.CREW.toLowerCase());
        castMovies.addAll(crewMovies);
        return castMovies.stream().distinct().collect(Collectors.toList());
    }

    @GetMapping(value = "/person/movies")
    public Map<String, List<Movies>> personMovies(@RequestParam String personId) {
        return getPersonService().getPersonMoviesByPersonId(personId);
    }

    @GetMapping(value = "/person/shows")
    public Map<String, List<Shows>> personShows(@RequestParam String personId) {
        return getPersonService().getPersonShowsByPersonId(personId);
    }
}
