package com.movie.kit.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Episodes {
    public String air_date;
    public Integer episode_number;
    public String id;
    public String name;
    public String overview;
    public String production_code;
    public Integer runtime;
    public Integer season_number;
    public Integer show_id;
    public String still_path;
    public double vote_average = 0.0;
    public Integer vote_count;
}
