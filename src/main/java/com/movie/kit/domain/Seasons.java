package com.movie.kit.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Seasons {
    public String seasonId;
    public String seasonName;
    public Integer seasonNumber;
    public String seasonReleaseDate;
    public String seasonPoster;
    private String seasonRating;
}
