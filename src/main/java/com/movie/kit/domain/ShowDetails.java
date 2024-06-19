package com.movie.kit.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowDetails {
    private String showId;
    private String showName;
    private String showOriginalName;
    private String showTagName;
    private String firstReleaseDate;
    private String lastReleaseDate;
    private String showRating;
    private String showBanner;
    private String showPoster;
    private String showPopularity;
    private List<Genres> genres;
    private String showOverview;
    private String showDuration;
    private String numberOfSeasons;
    private String numberOfEpisodes;
    private String showImdbId;
    private String showOriginalLanguage;
    private String showStatus;
    private String showDirectors;
    private String showProducers;
    private List<Trailers> showTrailers;
    private List<CastCrew> showCasts;
    private List<CastCrew> showCrews;
    private List<Seasons> seasons;
    private List<Episodes> episodes;
}
