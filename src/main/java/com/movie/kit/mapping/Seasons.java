package com.movie.kit.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Seasons {
    public String air_date;
    public String episode_count;
    public String id;
    public String name;
    public String overview;
    public String poster_path;
    public Integer season_number;
    public double vote_average = 0.0;
}
