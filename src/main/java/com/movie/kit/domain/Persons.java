package com.movie.kit.domain;

import lombok.*;

@Builder
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Persons {
    private String personId;
    private String personName;
    private String personPoster;
    private String personDateOfBirth;
    private String personPopularity;
}
