package com.movie.kit.rest;

import com.movie.kit.autowired.AutowiredMappedService;
import com.movie.kit.domain.Movies;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class MovieController extends AutowiredMappedService {

    @GetMapping(value = "/movies")
    public List<Movies> movies(@RequestParam String movieType, @RequestParam String pageIndex) {
        List<Movies> movies = getMovieService().getMoviesByMovieTypeAndPageIndex(movieType, pageIndex)
                .stream().distinct().collect(Collectors.toList());
        Collections.shuffle(movies);
        return movies;
    }

    @GetMapping(value = "/movie/recommendations")
    public List<Movies> movieRecommendations(@RequestParam String movieId, @RequestParam String pageIndex) {
        return getMovieService().getRecommendationMoviesByMovieIdAndPageIndex(movieId, pageIndex);
    }
}
