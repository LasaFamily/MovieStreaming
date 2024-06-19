package com.movie.kit.rest;

import com.movie.kit.autowired.AutowiredMappedService;
import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Persons;
import com.movie.kit.domain.Shows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/search")
public class SearchController extends AutowiredMappedService {

    @GetMapping(value = "/movies")
    public List<Movies> movies(@RequestParam String search, @RequestParam String pageIndex) {
        return getSearchService().getMoviesBySearchAndPageIndex(search, pageIndex);
    }

    @GetMapping(value = "/shows")
    public List<Shows> shows(@RequestParam String search, @RequestParam String pageIndex) {
        return getSearchService().getShowsBySearchAndPageIndex(search, pageIndex);
    }

    @GetMapping(value = "/persons")
    public List<Persons> persons(@RequestParam String search, @RequestParam String pageIndex) {
        return getSearchService().getPersonsBySearchAndPageIndex(search, pageIndex);
    }
}
