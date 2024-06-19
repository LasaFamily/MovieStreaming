package com.movie.kit.domain;

import lombok.*;

import java.util.List;

@Builder
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetails {
    private String movieId;
    private String movieName;
    private String moviePoster;
    private String movieBanner;
    private Double movieRating;
    private String movieReleaseDate;
    private List<Genres> genres;
    private String movieBudget;
    private String movieHomepage;
    private String movieImdbId;
    private String movieOverview;
    private String moviePopularity;
    private String movieOriginalName;
    private String movieTagName;
    private String movieCollection;
    private String movieOriginalLanguage;
    private String movieStatus;
    private String movieDuration;
    private String movieDirectors;
    private String movieProducers;
    private List<CastCrew> movieCasts;
    private List<CastCrew> movieCrews;
    private List<Trailers> movieTrailers;
    private List<MovieStreaming> movieStreaming;
}
