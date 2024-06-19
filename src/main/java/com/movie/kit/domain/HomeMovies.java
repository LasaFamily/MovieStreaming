package com.movie.kit.domain;

import lombok.*;

import java.util.List;

@Builder
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HomeMovies {
    private List<Movies> popularMovies;
    private List<Movies> topRatedMovies;
    private List<Movies> topWeeklyMovies;
    private List<Movies> nowPlayingMovies;
    private List<Movies> upcomingMovies;
    private List<Genres> movieGenres;
}
