package com.movie.kit.controller;

import com.movie.kit.api.RestClient;
import com.movie.kit.constants.MovieUrls;
import com.movie.kit.domain.Genres;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GenresController extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;

    @GetMapping(value = "/genres")
    public String genres(Model model) {
        List<Genres> movieGenres = get(hostUrl + MovieUrls.GENRES_END_POINT + "?genreType=movie", new ParameterizedTypeReference<>() {});
        List<Genres> showGenres = get(hostUrl + MovieUrls.GENRES_END_POINT + "?genreType=tv", new ParameterizedTypeReference<>() {});
        model.addAttribute("movieGenres", movieGenres);
        model.addAttribute("showGenres", showGenres);
        model.addAttribute("userSearch", "movies");
        return "genre/genres";
    }
}
