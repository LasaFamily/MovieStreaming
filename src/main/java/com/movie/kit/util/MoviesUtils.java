package com.movie.kit.util;

import com.movie.kit.domain.Genres;
import com.movie.kit.domain.Movies;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MoviesUtils {
    public static List<Movies> getMovies(Map<Integer, Genres> genresMap, List<com.movie.kit.mapping.Movies> movies) {
        return movies.stream().map(movie -> getMovies(genresMap, movie)).collect(Collectors.toList());
    }

    private static Movies getMovies(Map<Integer, Genres> genresMap, com.movie.kit.mapping.Movies movie) {
        return Movies.builder()
                .movieId(movie.getId())
                .movieName(movie.getTitle())
                .movieBanner(movie.getBackdrop_path())
                .movieGenres((!movie.getGenre_ids().isEmpty() && movie.getGenre_ids().size() > 0) ?
                        movie.getGenre_ids().stream().map(genresMap::get).map(Genres::getGenericName)
                                .collect(Collectors.joining(", ")) : null)
                .moviePoster(movie.getPoster_path())
                .movieRating(Double.parseDouble(String.valueOf(BigDecimal
                        .valueOf(Double.parseDouble(movie.getVote_average()))
                        .setScale(1, RoundingMode.UP))))
                .movieReleaseDate(CommonUtil.formatReleaseDate(movie.getRelease_date()))
                .build();
    }
}
