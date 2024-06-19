package com.movie.kit.service;

import com.movie.kit.domain.Movies;

import java.util.List;

public interface MovieService {
    List<Movies> getMoviesByMovieTypeAndPageIndex(String movieType, String pageIndex);
    List<Movies> getRecommendationMoviesByMovieIdAndPageIndex(String movieId, String pageIndex);
}
