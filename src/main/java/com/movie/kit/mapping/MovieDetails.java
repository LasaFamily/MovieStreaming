package com.movie.kit.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDetails {
    public String id;
    public String title;
    public String poster_path;
    public String backdrop_path;
    public String vote_average;
    public List<Integer> genre_ids;
    public String release_date;
    public String original_title;
    public String tagline;
    public String popularity;
    public List<Genres> genres;
    public String overview;
    public Integer runtime;
    public String budget;
    public String revenue;
    public String imdb_id;
    public String original_language;
    public String status;
    public String homepage;
    public String vote_count;
    public boolean video;
    public List<Trailers> movieTrailers;
    public List<Images> movieImages;
    public Map<String, List<Persons>> persons;
}
