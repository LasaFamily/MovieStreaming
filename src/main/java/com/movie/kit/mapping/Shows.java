package com.movie.kit.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shows {
    public String id;
    public String name;
    public String poster_path;
    public String backdrop_path;
    public String vote_average;
    public List<Integer> genre_ids;
    public String first_air_date;
}
