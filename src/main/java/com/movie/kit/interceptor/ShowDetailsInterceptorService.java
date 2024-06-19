package com.movie.kit.interceptor;

import com.movie.kit.mapping.Episodes;
import com.movie.kit.mapping.ShowDetails;

import java.util.List;

public interface ShowDetailsInterceptorService {
    ShowDetails getShowDetailsByShowId(String showId);
    List<Episodes> getEpisodesByShowIdAndSeasonId(String showId, Integer seasonNumber);
}
