package com.movie.kit.rest;

import com.movie.kit.autowired.AutowiredMappedService;
import com.movie.kit.domain.Genres;
import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Shows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class GenreController extends AutowiredMappedService {

    @GetMapping(value = "/genres")
    public List<Genres> movies(@RequestParam String genreType) {
        return getGenreService().getAllGenericsByType(genreType);
    }

    @GetMapping(value = "/genre/movies")
    public List<Movies> genreMovies(@RequestParam String genreId, @RequestParam String pageIndex) {
        return getGenreService().getMoviesByGenreIdAndPageIndex(genreId, pageIndex).stream().distinct().collect(Collectors.toList());
    }

    @GetMapping(value = "/genre/shows")
    public List<Shows> genreShows(@RequestParam String genreId, @RequestParam String pageIndex) {
        return getGenreService().getShowsByGenreIdAndPageIndex(genreId, pageIndex);
    }


}
