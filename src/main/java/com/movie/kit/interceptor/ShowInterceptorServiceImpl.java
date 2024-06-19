package com.movie.kit.interceptor;

import com.movie.kit.autowired.AutowiredAndValueService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.mapping.Shows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowInterceptorServiceImpl extends AutowiredAndValueService implements ShowInterceptorService {

    @Override
    public List<Shows> getShowsByShowTypeAndPageIndex(String showType, String pageIndex) {
        return getRestService().restClient(getRestUrl(showType, pageIndex), getBearerToken(), ApiConstants.RESULTS, ApiConstants.SHOWS);
    }

    @Override
    public List<Shows> getRecommendationShowsByShowIdAndPageIndex(String showId, String pageIndex) {
        return getRestService().restClient(getRecommendationRestUrl(showId, pageIndex), getBearerToken(), ApiConstants.RESULTS, ApiConstants.SHOWS);
    }

    private String getRecommendationRestUrl(String showId, String pageIndex) {
        return getBaseUrl() + ApiConstants.SLASH + ApiConstants.TV + ApiConstants.SLASH + showId +
                ApiConstants.SLASH + ApiConstants.RECOMMENDATIONS + ApiConstants.QUESTION_MARK + ApiConstants.PAGE + ApiConstants.EQUALS + pageIndex;
    }

    private String getRestUrl(String showType, String pageIndex) {
        String restUrl = getBaseUrl() + ApiConstants.SLASH;
        if(isWeeklyOrDay(showType)) {
            restUrl = restUrl + ApiConstants.TRENDING + ApiConstants.SLASH;
        }
        restUrl = restUrl + ApiConstants.TV + ApiConstants.SLASH;
        if(showType.equalsIgnoreCase(ApiConstants.WEEK) || showType.equalsIgnoreCase(ApiConstants.DAY)) {
            restUrl = restUrl + showType.toLowerCase();
        } else {
            restUrl = restUrl + showType + ApiConstants.QUESTION_MARK + ApiConstants.PAGE + ApiConstants.EQUALS + pageIndex;
        }
        return restUrl;
    }
    private boolean isWeeklyOrDay(String movieType) {
        return movieType.equalsIgnoreCase(ApiConstants.WEEK) || movieType.equalsIgnoreCase(ApiConstants.DAY);
    }
}
