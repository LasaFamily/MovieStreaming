package com.movie.kit.controller;

import com.movie.kit.api.RestClient;
import com.movie.kit.constants.MovieUrls;
import com.movie.kit.domain.MovieDetails;
import com.movie.kit.domain.Movies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MovieDetailsController extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;

    @GetMapping(value = "/movie/details/{movieId}")
    public String movies(@PathVariable String movieId, Model model) {
        MovieDetails movie = get(hostUrl + MovieUrls.MOVIE_DETAILS_END_POINT + "?movieId=" + movieId, new ParameterizedTypeReference<>() {});
        List<Movies> movies = get(hostUrl + MovieUrls.MOVIE_RECOMMENDATIONS_END_POINT + "?movieId=" + movieId + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        model.addAttribute("movieId", movieId);
        model.addAttribute("movie", movie);
        model.addAttribute("movies", movies);
        model.addAttribute("title", "movies");
        model.addAttribute("userSearch", "movies");
        return "movies/movieDetails";
    }
}
