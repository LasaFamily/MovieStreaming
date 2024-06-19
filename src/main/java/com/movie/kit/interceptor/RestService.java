package com.movie.kit.interceptor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.mapping.*;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.security.Key;
import java.util.*;

@Service
public class RestService {

    public <T> List<T> restClient(String restUrl, String barerToken, String resultType, String requestType) {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(restUrl).header(ApiConstants.AUTHORIZATION, barerToken).asJson();
            if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return new ArrayList<>();
            }
            JSONArray jsonArray = resultType.equalsIgnoreCase(ApiConstants.EMPTY) ? jsonResponse.getBody().getArray() :
                    jsonResponse.getBody().getObject().getJSONArray(getResponseParam(resultType));
            Gson gson = new Gson();
            return gson.fromJson(jsonArray.toString(), getType(requestType));
        } catch (UnirestException e) {
            return new ArrayList<>();
        }
    }

    public <T> Map<String, List<T>> restClient(String restUrl, String barerToken, String requestType) {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(restUrl).header(ApiConstants.AUTHORIZATION, barerToken).asJson();
            if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return new HashMap<>();
            }
            JSONArray castArray = jsonResponse.getBody().getObject().getJSONArray(ApiConstants.CAST.toLowerCase());
            JSONArray crewArray = jsonResponse.getBody().getObject().getJSONArray(ApiConstants.CREW.toLowerCase());
            Gson gson = new Gson();
            Map<String, List<T>> map = new LinkedHashMap<>();
            map.put(ApiConstants.CAST.toLowerCase(), gson.fromJson(castArray.toString(), getType(requestType)));
            map.put(ApiConstants.CREW.toLowerCase(), gson.fromJson(crewArray.toString(), getType(requestType)));
            return map;
        } catch (UnirestException e) {
            return new HashMap<>();
        }
    }

    public Persons restClientForDetails(String restUrl, String barerToken) {
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(restUrl).header(ApiConstants.AUTHORIZATION, barerToken).asJson();
            if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return new Persons();
            }
            JSONObject personDetails = jsonResponse.getBody().getObject();
            Gson gson = new Gson();
            Type type = new TypeToken<Persons>() {
            }.getType();
            return gson.fromJson(personDetails.toString(), type);
        } catch (UnirestException e) {
            return new Persons();
        }
    }

    private Type getType(String resultType) {
        return resultType.equalsIgnoreCase(ApiConstants.MOVIES) ?
                new TypeToken<List<Movies>>() {
                }.getType() :
                resultType.equalsIgnoreCase(ApiConstants.SHOWS) ?
                        new TypeToken<List<Shows>>() {
                        }.getType() :
                        resultType.equalsIgnoreCase(ApiConstants.PERSONS) ?
                                new TypeToken<List<Persons>>() {
                                }.getType() :
                                resultType.equalsIgnoreCase(ApiConstants.COUNTRIES) ?
                                        new TypeToken<List<Countries>>() {
                                        }.getType() :
                                        new TypeToken<List<Genres>>() {
                                        }.getType();
    }

    private String getResponseParam(String resultType) {
        return resultType.toLowerCase();
    }
}
