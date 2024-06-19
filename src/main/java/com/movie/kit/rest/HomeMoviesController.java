package com.movie.kit.rest;

import com.google.gson.JsonObject;
import com.movie.kit.api.RestClient;
import com.movie.kit.constants.MovieUrls;
import com.movie.kit.domain.Genres;
import com.movie.kit.domain.HomeMovies;
import com.movie.kit.domain.Movies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class HomeMoviesController extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;


    @CrossOrigin(origins="*")
    @GetMapping(value = "/home/movies")
    public ResponseEntity<HomeMovies> homeMovies(Model model) {
        List<Movies> popularMovies = get(hostUrl + MovieUrls.MOVIES_END_POINT + "?movieType=popular" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Movies> topRatedMovies = get(hostUrl + MovieUrls.MOVIES_END_POINT + "?movieType=top_rated" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Movies> topWeeklyMovies = get(hostUrl + MovieUrls.MOVIES_END_POINT + "?movieType=week" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Movies> nowPlayingMovies = get(hostUrl + MovieUrls.MOVIES_END_POINT + "?movieType=now_playing" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Movies> upcomingMovies = get(hostUrl + MovieUrls.MOVIES_END_POINT + "?movieType=upcoming" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Genres> movieGenres = get(hostUrl + MovieUrls.GENRES_END_POINT + "?genreType=movie", new ParameterizedTypeReference<>() {});
        Collections.shuffle(movieGenres);
        return new ResponseEntity<>(new HomeMovies(popularMovies, topRatedMovies, topWeeklyMovies, nowPlayingMovies, upcomingMovies, movieGenres), HttpStatus.OK);
    }
}
