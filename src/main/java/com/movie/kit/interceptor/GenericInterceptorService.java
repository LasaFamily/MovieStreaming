package com.movie.kit.interceptor;

import com.movie.kit.mapping.Genres;
import com.movie.kit.mapping.Movies;
import com.movie.kit.mapping.Shows;

import java.util.List;

public interface GenericInterceptorService {
    List<Genres> getAllGenericsByType(String genericType);
    List<Movies> getMoviesByGenreIdAndPageIndex(String genreId, String pageIndex);
    List<Shows> getShowsByGenreIdAndPageIndex(String genreId, String pageIndex);
}
