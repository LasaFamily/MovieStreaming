package com.movie.kit.rest;

import com.movie.kit.autowired.AutowiredMappedService;
import com.movie.kit.domain.MovieDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class MovieDetailController extends AutowiredMappedService {

    @GetMapping(value = "/movie/details")
    public MovieDetails movieDetails(@RequestParam String movieId) {
        return getMovieDetailsService().getMovieDetailsByMovieId(movieId);
    }
}