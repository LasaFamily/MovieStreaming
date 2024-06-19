package com.movie.kit.mapping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Persons {
    public String id;
    public String name;
    public String profile_path;
    public String popularity;
    private String job;
    private Integer order;
    private String character;
    private String department;
    private String birthday;
}
