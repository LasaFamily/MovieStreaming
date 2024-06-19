package com.movie.kit.jsoup;

import com.movie.kit.model.Movies;

import java.util.List;

public interface MovieJsoupService {
    List<Movies> getMovies(Integer pageNumber, Integer endIndex, Integer movieNumber);
    List<Movies> getLookMovies(Integer pageNumber, Integer movieNumber);
}
