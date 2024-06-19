package com.movie.kit.service;

import com.movie.kit.autowired.AutowiredService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.domain.Genres;
import com.movie.kit.domain.Movies;
import com.movie.kit.util.MoviesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl extends AutowiredService implements MovieService {

    @Autowired
    private GenreService genreService;

    @Override
    public List<Movies> getMoviesByMovieTypeAndPageIndex(String movieType, String pageIndex) {
        return MoviesUtils.getMovies(genreService.getAllGenericsByType(ApiConstants.MOVIE)
                        .stream().collect(Collectors.toMap(Genres::getGenericId, genres -> genres)),
                getMovieInterceptorService().getMoviesByMovieTypeAndPageIndex(movieType, pageIndex));
    }

    @Override
    public List<Movies> getRecommendationMoviesByMovieIdAndPageIndex(String movieId, String pageIndex) {
        return MoviesUtils.getMovies(genreService.getAllGenericsByType(ApiConstants.MOVIE)
                        .stream().collect(Collectors.toMap(Genres::getGenericId, genres -> genres)),
                getMovieInterceptorService().getRecommendationMoviesByMovieIdAndPageIndex(movieId, pageIndex));
    }
}
