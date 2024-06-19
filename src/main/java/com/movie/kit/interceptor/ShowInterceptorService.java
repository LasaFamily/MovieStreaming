package com.movie.kit.interceptor;

import com.movie.kit.mapping.Shows;

import java.util.List;

public interface ShowInterceptorService {
    List<Shows> getShowsByShowTypeAndPageIndex(String showType, String pageIndex);
    List<Shows> getRecommendationShowsByShowIdAndPageIndex(String showId, String pageIndex);
}
