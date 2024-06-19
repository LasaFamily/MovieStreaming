package com.movie.kit.interceptor;

import com.movie.kit.autowired.AutowiredAndValueService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.mapping.Movies;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieInterceptorServiceImpl extends AutowiredAndValueService implements MovieInterceptorService {

    @Override
    public List<Movies> getMoviesByMovieTypeAndPageIndex(String movieType, String pageIndex) {
        return getRestService().restClient(getRestUrl(movieType, pageIndex), getBearerToken(), ApiConstants.RESULTS, ApiConstants.MOVIES);
    }

    @Override
    public List<Movies> getRecommendationMoviesByMovieIdAndPageIndex(String movieId, String pageIndex) {
        return getRestService().restClient(getRecommendationRestUrl(movieId, pageIndex), getBearerToken(), ApiConstants.RESULTS, ApiConstants.MOVIES);
    }

    private String getRecommendationRestUrl(String movieId, String pageIndex) {
        return getBaseUrl() + ApiConstants.SLASH + ApiConstants.MOVIE + ApiConstants.SLASH + movieId +
                ApiConstants.SLASH + ApiConstants.RECOMMENDATIONS + ApiConstants.QUESTION_MARK + ApiConstants.PAGE + ApiConstants.EQUALS + pageIndex;
    }

    private String getRestUrl(String movieType, String pageIndex) {
        String restUrl = getBaseUrl() + ApiConstants.SLASH;
        if(isWeeklyOrDay(movieType)) {
            restUrl = restUrl + ApiConstants.TRENDING + ApiConstants.SLASH;
        }
        restUrl = restUrl + ApiConstants.MOVIE + ApiConstants.SLASH;
        if(movieType.equalsIgnoreCase(ApiConstants.WEEK) || movieType.equalsIgnoreCase(ApiConstants.DAY)) {
            restUrl = restUrl + movieType.toLowerCase();
        } else {
            restUrl = restUrl + movieType + ApiConstants.QUESTION_MARK + ApiConstants.PAGE + ApiConstants.EQUALS + pageIndex;
        }
        return restUrl;
    }

    private boolean isWeeklyOrDay(String movieType) {
        return movieType.equalsIgnoreCase(ApiConstants.WEEK) || movieType.equalsIgnoreCase(ApiConstants.DAY);
    }
}
