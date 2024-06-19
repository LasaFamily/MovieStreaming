package com.movie.kit.controller;

import com.movie.kit.api.RestClient;
import com.movie.kit.constants.MovieUrls;
import com.movie.kit.domain.Genres;
import com.movie.kit.domain.Shows;
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
public class GenreShowsController extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;

    @GetMapping(value = "/genre/shows/{genreId}")
    public String shows(@PathVariable Integer genreId, Model model) {
        List<Genres> movieGenres = get(hostUrl + MovieUrls.GENRES_END_POINT + "?genreType=tv", new ParameterizedTypeReference<>() {});
        model.addAttribute("pageIndex", 1);
        model.addAttribute("genreId", genreId);
        model.addAttribute("title", "genre shows");
        model.addAttribute("viewName", movieGenres.stream()
                .filter(genres -> Objects.equals(genres.getGenericId(), genreId)).map(Genres::getGenericName).findFirst().orElse("Shows"));
        model.addAttribute("userSearch", "shows");
        return "genre/shows";
    }

    @GetMapping(value = "/genre/shows/append")
    public String genreShowsAppend(@RequestParam String genreId, @RequestParam Integer pageIndex, Model model) {
        List<Shows> shows = get(hostUrl + MovieUrls.GENRE_SHOWS_END_POINT + "?genreId=" + genreId + "&pageIndex=" + pageIndex, new ParameterizedTypeReference<>() {});
        model.addAttribute("genreId", genreId);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("shows", shows);
        return pageIndex % 2 == 0 ? "shows/evenAppendShows" : "shows/oddAppendShows";
    }
}
