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
public class ShowDetails {
    public String id;
    public String backdrop_path;
    public List<Integer> episode_run_time;
    public String first_air_date;
    public List<Genres> genres;
    public String last_air_date;
    public String name;
    public String number_of_episodes;
    public String number_of_seasons;
    public String original_language;
    public String original_name;
    public String overview;
    public String popularity;
    public String poster_path;
    public String tagline;
    public String imdb_id;
    public String status;
    public String type;
    public String vote_average;
    public List<Trailers> showTrailers;
    public List<Images> showImages;
    public List<Seasons> seasons;
    public String runtime;
    public Map<String, List<Persons>> persons;
}
