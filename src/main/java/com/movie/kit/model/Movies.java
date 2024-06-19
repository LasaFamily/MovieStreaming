package com.movie.kit.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "movies")
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id")
    private Long id;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "movie_release_date")
    private String movieReleaseDate;

    @Column(name = "movie_streaming_id")
    private String movieStreamingId;

    @Column(name = "movie_imdb_id")
    private String movieImdbId;

    @Column(name = "movie_server_ids")
    private String movieServerIds;

    @Column(name = "look_movie_id")
    private String lookMovieId;
}
