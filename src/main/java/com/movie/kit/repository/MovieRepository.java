package com.movie.kit.repository;

import com.movie.kit.model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movies, Long> {
    Movies findByMovieImdbId(String imdbId);
    List<Movies> findByMovieNameAndMovieReleaseDate(String movieName, String movieYear);
}
