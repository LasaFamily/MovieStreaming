package com.movie.kit.domain;

import lombok.*;

import java.util.Objects;

@Builder
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Movies {
    private String movieId;
    private String movieName;
    private String moviePoster;
    private String movieBanner;
    private Double movieRating;
    private String movieGenres;
    private String movieReleaseDate;
    private Integer movieIndex;
    private String movieNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movies movies = (Movies) o;
        return movieId.equals(movies.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId);
    }
}
