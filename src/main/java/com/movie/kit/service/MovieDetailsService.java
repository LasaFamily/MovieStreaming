package com.movie.kit.service;

import com.movie.kit.domain.MovieDetails;

public interface MovieDetailsService {
    MovieDetails getMovieDetailsByMovieId(String movieId);
}
