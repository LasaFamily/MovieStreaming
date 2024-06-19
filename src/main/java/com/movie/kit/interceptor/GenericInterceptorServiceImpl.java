package com.movie.kit.interceptor;

import com.movie.kit.autowired.AutowiredAndValueService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.mapping.Genres;
import com.movie.kit.mapping.Movies;
import com.movie.kit.mapping.Shows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericInterceptorServiceImpl extends AutowiredAndValueService implements GenericInterceptorService {

    @Override
    public List<Genres> getAllGenericsByType(String genericType) {
        String restUrl = getBaseUrl() + ApiConstants.SLASH + ApiConstants.GENRE +
                ApiConstants.SLASH + genericType + ApiConstants.SLASH +  ApiConstants.LIST;
        return getRestService().restClient(restUrl, getBearerToken(), ApiConstants.GENRES, ApiConstants.GENRES);
    }

    @Override
    public List<Movies> getMoviesByGenreIdAndPageIndex(String genreId, String pageIndex) {
        return getRestService().restClient(getRestUrl(ApiConstants.MOVIE, pageIndex, genreId), getBearerToken(), ApiConstants.RESULTS, ApiConstants.MOVIES);
    }

    @Override
    public List<Shows> getShowsByGenreIdAndPageIndex(String genreId, String pageIndex) {
        return getRestService().restClient(getRestUrl(ApiConstants.TV, pageIndex, genreId), getBearerToken(), ApiConstants.RESULTS, ApiConstants.SHOWS);
    }

    private String getRestUrl(String genreType, String pageIndex, String genreId) {
        return getBaseUrl() + ApiConstants.SLASH + ApiConstants.DISCOVER + ApiConstants.SLASH + genreType +
                ApiConstants.QUESTION_MARK + ApiConstants.PAGE + ApiConstants.EQUALS + pageIndex +
                ApiConstants.AND + ApiConstants.WITH_GENRES + ApiConstants.EQUALS + genreId + ApiConstants.AND +
                ApiConstants.SORT_BY + ApiConstants.EQUALS +  ApiConstants.POPULARITY_DESC;
    }
}
