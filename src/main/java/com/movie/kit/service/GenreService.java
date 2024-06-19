package com.movie.kit.service;

import com.movie.kit.domain.Genres;
import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Shows;

import java.util.List;

public interface GenreService {
    List<Genres> getAllGenericsByType(String genericType);
    List<Movies> getMoviesByGenreIdAndPageIndex(String genreId, String pageIndex);
    List<Shows> getShowsByGenreIdAndPageIndex(String genreId, String pageIndex);
}
