package com.movie.kit.interceptor;

import com.movie.kit.autowired.AutowiredAndValueService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.mapping.Movies;
import com.movie.kit.mapping.Persons;
import com.movie.kit.mapping.Shows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PersonInterceptorServiceImpl extends AutowiredAndValueService implements PersonInterceptorService {

    @Override
    public List<Persons> getPersonsByPageIndex(String pageIndex) {
        return getRestService().restClient(getRestUrl(pageIndex), getBearerToken(), ApiConstants.RESULTS, ApiConstants.PERSONS);
    }

    @Override
    public Map<String, List<Movies>> getPersonMoviesByPersonId(String personId) {
        return getRestService().restClient(getPersonRestUrl(personId, ApiConstants.MOVIE_CREDIT), getBearerToken(), ApiConstants.MOVIES);
    }

    @Override
    public Map<String, List<Shows>> getPersonShowsByPersonId(String personId) {
        return getRestService().restClient(getPersonRestUrl(personId, ApiConstants.TV_CREDIT), getBearerToken(), ApiConstants.SHOWS);
    }

    @Override
    public Persons getPersonDetailsByPersonId(String personId) {
        return getRestService().restClientForDetails(getPersonDetailsRestUrl(personId), getBearerToken());
    }

    private String getPersonDetailsRestUrl(String personId) {
        return getBaseUrl() + ApiConstants.SLASH + ApiConstants.PERSON + ApiConstants.SLASH + personId;
    }



    private String getPersonRestUrl(String personId, String creditType) {
        return getBaseUrl() + ApiConstants.SLASH + ApiConstants.PERSON + ApiConstants.SLASH + personId +
                ApiConstants.SLASH + creditType;
    }

    private String getRestUrl(String pageIndex) {
        return getBaseUrl() + ApiConstants.SLASH + ApiConstants.PERSON + ApiConstants.SLASH + ApiConstants.POPULAR +
                ApiConstants.QUESTION_MARK + ApiConstants.PAGE + ApiConstants.EQUALS + pageIndex;
    }
}
