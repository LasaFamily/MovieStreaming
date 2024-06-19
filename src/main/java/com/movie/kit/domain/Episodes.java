package com.movie.kit.domain;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Episodes {
    private String episodeId;
    private String episodeName;
    private Integer episodeNumber;
    private String episodeImage;
    private String episodeRunTime;
    private String episodeRating;
}
