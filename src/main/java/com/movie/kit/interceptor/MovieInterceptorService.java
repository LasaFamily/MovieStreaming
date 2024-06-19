package com.movie.kit.interceptor;

import com.movie.kit.mapping.Movies;

import java.util.List;

public interface MovieInterceptorService {
     List<Movies> getMoviesByMovieTypeAndPageIndex(String movieType, String pageIndex);
    List<Movies> getRecommendationMoviesByMovieIdAndPageIndex(String movieId, String pageIndex);
}
