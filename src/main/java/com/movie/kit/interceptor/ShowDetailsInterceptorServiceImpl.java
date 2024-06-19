package com.movie.kit.interceptor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movie.kit.autowired.AutowiredAndValueService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.mapping.*;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShowDetailsInterceptorServiceImpl extends AutowiredAndValueService implements ShowDetailsInterceptorService {

    @Override
    public ShowDetails getShowDetailsByShowId(String showId) {
        String url = getBaseUrl() + ApiConstants.SLASH + ApiConstants.TV + ApiConstants.SLASH + showId +
                ApiConstants.QUESTION_MARK + ApiConstants.APPEND_TO_RESPONSE + ApiConstants.EQUALS +
                ApiConstants.PARAMS;
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url)
                    .header(ApiConstants.AUTHORIZATION, getBearerToken()).asJson();
            if(jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return new ShowDetails();
            }
            Gson gson = new Gson();
            Type type = new TypeToken<ShowDetails>() {}.getType();
            ShowDetails showDetails = gson.fromJson(jsonResponse.getBody().toString(), type);
            JSONArray trailers = jsonResponse.getBody().getObject().getJSONObject(ApiConstants.VIDEOS)
                    .getJSONArray(ApiConstants.RESULTS.toLowerCase());
            Type trailer = new TypeToken<List<Trailers>>() {}.getType();
            List<Trailers> showTrailers = gson.fromJson(trailers.toString(), trailer);
            showDetails.setShowTrailers(showTrailers);
            JSONArray images = jsonResponse.getBody().getObject().getJSONObject(ApiConstants.IMAGES)
                    .getJSONArray(ApiConstants.POSTERS);
            Type image = new TypeToken<List<Images>>() {}.getType();
            List<Images> showImages = gson.fromJson(images.toString(), image);
            showDetails.setShowImages(showImages);
            JSONObject externalIds = jsonResponse.getBody().getObject().getJSONObject(ApiConstants.EXTERNAL_IDS);
            if(!externalIds.isNull(ApiConstants.IMDB_ID)) {
                String imdbId = jsonResponse.getBody().getObject().getJSONObject(ApiConstants.EXTERNAL_IDS)
                        .getString(ApiConstants.IMDB_ID);
                showDetails.setImdb_id(imdbId);
            }
            JSONArray casts = jsonResponse.getBody().getObject().getJSONObject(ApiConstants.CREDITS)
                    .getJSONArray(ApiConstants.CAST.toLowerCase());
            JSONArray crews = jsonResponse.getBody().getObject().getJSONObject(ApiConstants.CREDITS)
                    .getJSONArray(ApiConstants.CREW.toLowerCase());
            Type castCrew = new TypeToken<List<Persons>>() {}.getType();
            Map<String, List<Persons>> persons = new LinkedHashMap<>();
            persons.put(ApiConstants.CAST.toLowerCase(), gson.fromJson(casts.toString(), castCrew));
            persons.put(ApiConstants.CREW.toLowerCase(), gson.fromJson(crews.toString(), castCrew));
            showDetails.setPersons(persons);
            return showDetails;
        } catch (UnirestException e) {
            return new ShowDetails();
        }
    }

    @Override
    public List<Episodes> getEpisodesByShowIdAndSeasonId(String showId, Integer seasonNumber) {
        String url = getBaseUrl() + ApiConstants.SLASH + ApiConstants.TV + ApiConstants.SLASH + showId +
                ApiConstants.SLASH + ApiConstants.SEASON + ApiConstants.SLASH + seasonNumber;
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url)
                    .header(ApiConstants.AUTHORIZATION, getBearerToken()).asJson();
            if(jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return new ArrayList<>();
            }
            JSONArray jsonArray = jsonResponse.getBody().getObject()
                    .getJSONArray(ApiConstants.EPISODES.toLowerCase());
            Gson gson = new Gson();
            Type type = new TypeToken<List<Episodes>>() {}.getType();
            return gson.fromJson(jsonArray.toString(), type);
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }
}
