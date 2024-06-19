package com.movie.kit.util;

import com.movie.kit.domain.Genres;
import com.movie.kit.domain.Shows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowUtils {
    public static List<Shows> getShows(Map<Integer, Genres> genresMap, List<com.movie.kit.mapping.Shows> shows) {
        return shows.stream().map(show -> getShows(genresMap, show)).collect(Collectors.toList());
    }

    private static Shows getShows(Map<Integer, Genres> genresMap, com.movie.kit.mapping.Shows show) {
        return Shows.builder()
                .showId(show.getId())
                .showName(show.getName())
                .showBanner(show.getBackdrop_path())
                .showGenres((!show.getGenre_ids().isEmpty() && show.getGenre_ids().size() > 0) ?
                        show.getGenre_ids().stream().map(genresMap::get).map(Genres::getGenericName)
                                .collect(Collectors.joining(", ")) : null)
                .showPoster(show.getPoster_path())
                .showRating(Double.parseDouble(String.valueOf(BigDecimal
                        .valueOf(Double.parseDouble(show.getVote_average()))
                        .setScale(1, RoundingMode.UP))))
                .showReleaseDate(CommonUtil.formatReleaseDate(show.getFirst_air_date()))
                .build();
    }
}
