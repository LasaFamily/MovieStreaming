package com.movie.kit.service;

import com.movie.kit.domain.Shows;

import java.util.List;

public interface ShowService {
    List<Shows> getShowsByShowTypeAndPageIndex(String showType, String pageIndex);
    List<Shows> getRecommendationShowByShowIdAndPageIndex(String showId, String pageIndex);
}
