package com.movie.kit.interceptor;

import com.movie.kit.autowired.AutowiredAndValueService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.mapping.Countries;
import com.movie.kit.mapping.Movies;
import com.movie.kit.mapping.Shows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryInterceptorServiceImpl extends AutowiredAndValueService implements CountryInterceptorService {
    @Override
    public List<Countries> getAllCountries() {
        String restUrl = getBaseUrl() + ApiConstants.SLASH + ApiConstants.CONFIGURATION + ApiConstants.SLASH + ApiConstants.COUNTRIES;
        return getRestService().restClient(restUrl, getBearerToken(), ApiConstants.EMPTY, ApiConstants.COUNTRIES);
    }

    @Override
    public List<Movies> getCountryMoviesByCountryCodeAndPageIndex(String countryCode, String pageIndex) {
        return getRestService().restClient(getRestUrl(countryCode, pageIndex, ApiConstants.MOVIE), getBearerToken(),
                ApiConstants.RESULTS, ApiConstants.MOVIES);
    }

    @Override
    public List<Shows> getCountryShowsByCountryCodeAndPageIndex(String countryCode, String pageIndex) {
        return getRestService().restClient(getRestUrl(countryCode, pageIndex, ApiConstants.TV), getBearerToken(),
                ApiConstants.RESULTS, ApiConstants.SHOWS);
    }

    private String getRestUrl(String countryCode, String pageIndex, String movieOrShow) {
        return getBaseUrl() + ApiConstants.SLASH + ApiConstants.DISCOVER + ApiConstants.SLASH + movieOrShow +
                ApiConstants.QUESTION_MARK + ApiConstants.PAGE + ApiConstants.EQUALS + pageIndex +
                ApiConstants.AND + ApiConstants.WITH_ORIGIN_COUNTRY + ApiConstants.EQUALS + countryCode +
                ApiConstants.AND + ApiConstants.SORT_BY + ApiConstants.EQUALS +  ApiConstants.POPULARITY_DESC;
    }
}
