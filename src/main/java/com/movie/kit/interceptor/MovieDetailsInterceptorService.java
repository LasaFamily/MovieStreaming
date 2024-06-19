package com.movie.kit.interceptor;

import com.movie.kit.domain.MovieStreaming;
import com.movie.kit.mapping.MovieDetails;
import com.movie.kit.model.Movies;

import java.util.List;

public interface MovieDetailsInterceptorService {
    MovieDetails getMovieDetailsByMovieId(String movieId);
    void getStreamingId(String imdbId, String movieName, String movieYear, List<MovieStreaming> movieStreaming);
}
