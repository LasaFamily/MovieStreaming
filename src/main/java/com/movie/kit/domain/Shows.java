package com.movie.kit.domain;

import lombok.*;

@Builder
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Shows {
    private String showId;
    private String showName;
    private String showPoster;
    private String showBanner;
    private Double showRating;
    private String showGenres;
    private String showReleaseDate;
    private Integer showIndex;
    private String showNumber;
}
