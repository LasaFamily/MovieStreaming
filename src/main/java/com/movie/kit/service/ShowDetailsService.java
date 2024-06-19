package com.movie.kit.service;

import com.movie.kit.domain.Episodes;
import com.movie.kit.domain.ShowDetails;

import java.util.List;

public interface ShowDetailsService {
    ShowDetails getShowDetailsByShowId(String showId);
    List<Episodes> getEpisodesByShowIdAndSeasonId(String showId, Integer seasonNumber);
    public List<String> getShowStreamingId(String showId, String seasonNumber, String episodeNumber);
}
