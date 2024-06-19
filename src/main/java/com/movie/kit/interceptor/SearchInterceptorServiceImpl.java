package com.movie.kit.interceptor;

import com.movie.kit.autowired.AutowiredAndValueService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.mapping.Movies;
import com.movie.kit.mapping.Persons;
import com.movie.kit.mapping.Shows;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SearchInterceptorServiceImpl extends AutowiredAndValueService implements SearchInterceptorService {

    @Override
    public List<Movies> getMoviesBySearchAndPageIndex(String search, String pageIndex) {
        return getRestService().restClient(getRestUrl(ApiConstants.MOVIE, search, pageIndex), getBearerToken(), ApiConstants.RESULTS, ApiConstants.MOVIES);
    }

    @Override
    public List<Shows> getShowsBySearchAndPageIndex(String search, String pageIndex) {
        return getRestService().restClient(getRestUrl(ApiConstants.TV, search, pageIndex), getBearerToken(), ApiConstants.RESULTS, ApiConstants.SHOWS);
    }

    @Override
    public List<Persons> getPersonsBySearchAndPageIndex(String search, String pageIndex) {
        return getRestService().restClient(getRestUrl(ApiConstants.PERSON, search, pageIndex), getBearerToken(), ApiConstants.RESULTS, ApiConstants.PERSONS);
    }


    private String getRestUrl(String searchType, String searchKey, String pageIndex) {
        return getBaseUrl() + ApiConstants.SLASH + ApiConstants.SEARCH + ApiConstants.SLASH + searchType +
                ApiConstants.QUESTION_MARK + ApiConstants.QUERY + ApiConstants.EQUALS +
                URLEncoder.encode(searchKey, StandardCharsets.UTF_8) + ApiConstants.AND + ApiConstants.PAGE +
                ApiConstants.EQUALS + pageIndex;

    }
}
