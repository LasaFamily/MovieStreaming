package com.movie.kit.controller;

import com.movie.kit.api.RestClient;
import com.movie.kit.constants.MovieUrls;
import com.movie.kit.domain.Genres;
import com.movie.kit.domain.Movies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class GenreMoviesController extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;

    @GetMapping(value = "/genre/movies/{genreId}")
    public String movies(@PathVariable Integer genreId, Model model) {
        List<Genres> movieGenres = get(hostUrl + MovieUrls.GENRES_END_POINT + "?genreType=movie", new ParameterizedTypeReference<>() {});
        model.addAttribute("pageIndex", 1);
        model.addAttribute("genreId", genreId);
        model.addAttribute("title", "genre movies");
        model.addAttribute("userSearch", "movies");
        model.addAttribute("viewName", movieGenres.stream()
                .filter(genres -> Objects.equals(genres.getGenericId(), genreId)).map(Genres::getGenericName).findFirst().orElse("Movies"));
        return "genre/movies";
    }

    @GetMapping(value = "/genre/movies/append")
    public String genreMoviesAppend(@RequestParam String genreId, @RequestParam Integer pageIndex, Model model) {
        List<Movies> movies = get(hostUrl + MovieUrls.GENRE_MOVIES_END_POINT + "?genreId=" + genreId + "&pageIndex=" + pageIndex, new ParameterizedTypeReference<>() {});
        model.addAttribute("genreId", genreId);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("movies", movies);
        return pageIndex % 2 == 0 ? "movies/evenAppendMovies" : "movies/oddAppendMovies";
    }
}
