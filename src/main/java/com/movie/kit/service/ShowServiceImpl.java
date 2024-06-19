package com.movie.kit.service;

import com.movie.kit.autowired.AutowiredService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.domain.Genres;
import com.movie.kit.domain.Shows;
import com.movie.kit.util.ShowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl extends AutowiredService implements ShowService {

    @Autowired
    private GenreService genreService;

    @Override
    public List<Shows> getShowsByShowTypeAndPageIndex(String showType, String pageIndex) {
        return ShowUtils.getShows(genreService.getAllGenericsByType(ApiConstants.TV)
                        .stream().collect(Collectors.toMap(Genres::getGenericId, genres -> genres)),
                getShowInterceptorService().getShowsByShowTypeAndPageIndex(showType, pageIndex));
    }

    @Override
    public List<Shows> getRecommendationShowByShowIdAndPageIndex(String showId, String pageIndex) {
        return ShowUtils.getShows(genreService.getAllGenericsByType(ApiConstants.TV)
                        .stream().collect(Collectors.toMap(Genres::getGenericId, genres -> genres)),
                getShowInterceptorService().getRecommendationShowsByShowIdAndPageIndex(showId, pageIndex));
    }
}
