package com.movie.kit.api;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {
    protected <S> S get(String hostUrl, ParameterizedTypeReference<S> parameterizedTypeReference) {
        RequestEntity<Void> request = RequestEntity
                .get(hostUrl)
                .accept(MediaType.APPLICATION_JSON).build();
        return new RestTemplate().exchange(request, parameterizedTypeReference).getBody();
    }
}
