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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MoviesController extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;

    @GetMapping(value = "/")
    public String home() {
        return "redirect:/movies";
    }



    @GetMapping(value = "/movies")
    public String homeMovies(Model model) {
        List<Movies> popularMovies = get(hostUrl + MovieUrls.MOVIES_END_POINT + "?movieType=popular" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Movies> topRatedMovies = get(hostUrl + MovieUrls.MOVIES_END_POINT + "?movieType=top_rated" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Movies> topWeeklyMovies = get(hostUrl + MovieUrls.MOVIES_END_POINT + "?movieType=week" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Movies> nowPlayingMovies = get(hostUrl + MovieUrls.MOVIES_END_POINT + "?movieType=now_playing" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Movies> upcomingMovies = get(hostUrl + MovieUrls.MOVIES_END_POINT + "?movieType=upcoming" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Genres> movieGenres = get(hostUrl + MovieUrls.GENRES_END_POINT + "?genreType=movie", new ParameterizedTypeReference<>() {});

        Collections.shuffle(popularMovies);
        Collections.shuffle(topRatedMovies);
        Collections.shuffle(topWeeklyMovies);
        Collections.shuffle(nowPlayingMovies);
        Collections.shuffle(upcomingMovies);
        model.addAttribute("popularMovies", popularMovies);
        model.addAttribute("topRatedMovies", topRatedMovies);
        model.addAttribute("topWeeklyMovies", getTopWeeklyMovies(topWeeklyMovies));
        model.addAttribute("nowPlayingMovies", nowPlayingMovies);
        model.addAttribute("upcomingMovies", upcomingMovies);
        model.addAttribute("movieGenres", movieGenres);
        model.addAttribute("title", "movies");
        model.addAttribute("userSearch", "movies");
        return "movies/home";
    }

    @GetMapping(value = "/movies/{movieType}")
    public String movies(@PathVariable String movieType, Model model) {
        model.addAttribute("movieType", movieType);
        model.addAttribute("pageIndex", 1);
        model.addAttribute("title", "movies");
        model.addAttribute("viewName", getViewName(movieType));
        model.addAttribute("userSearch", "movies");
        return "movies/movies";
    }

    private String getViewName(String movieType) {
        return movieType.equalsIgnoreCase("popular") ? "Popular Movies" :
                movieType.equalsIgnoreCase("top_rated") ? "Top Rated Movies" :
                        movieType.equalsIgnoreCase("now_playing") ? "Now Playing Movies" :
                                movieType.equalsIgnoreCase("upcoming") ? "Upcoming Movies" : "Movies";
    }

    @GetMapping(value = "/movies/append")
    public String moviesAppend(@RequestParam String movieType, @RequestParam Integer pageIndex, Model model) {
        List<Movies> movies = get(hostUrl + MovieUrls.MOVIES_END_POINT + "?movieType=" + movieType + "&pageIndex=" + pageIndex, new ParameterizedTypeReference<>() {});
        model.addAttribute("movieType", movieType);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("movies", movies);
        return pageIndex % 2 == 0 ? "movies/evenAppendMovies" : "movies/oddAppendMovies";
    }

    private Map<Integer, List<Movies>> getTopWeeklyMovies(List<Movies> topWeeklyMovies) {
        final Integer[] index = {1};
        Map<Integer, List<Movies>> movies = topWeeklyMovies.stream().peek(movie -> {
            if(index[0] <=5) {
                movie.setMovieIndex(1);
            } else if(index[0] <=10) {
                movie.setMovieIndex(2);
            } else if(index[0] <=15) {
                movie.setMovieIndex(3);
            } else if(index[0] <=20) {
                movie.setMovieIndex(4);
            }
            movie.setMovieNumber(index[0] < 10 ? ("0" + index[0]) : index[0].toString());
            index[0]++;
        }).collect(Collectors.groupingBy(Movies::getMovieIndex, Collectors.toList()));
        movies.remove(4);
        return movies;
    }
}
