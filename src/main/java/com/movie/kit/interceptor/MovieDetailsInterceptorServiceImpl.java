package com.movie.kit.interceptor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movie.kit.autowired.AutowiredAndValueService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.domain.MovieStreaming;
import com.movie.kit.mapping.*;
import com.movie.kit.model.Movies;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MovieDetailsInterceptorServiceImpl extends AutowiredAndValueService implements MovieDetailsInterceptorService {

    @Override
    public MovieDetails getMovieDetailsByMovieId(String movieId) {
        String url = getBaseUrl() + ApiConstants.SLASH + ApiConstants.MOVIE + ApiConstants.SLASH + movieId +
                ApiConstants.QUESTION_MARK + ApiConstants.APPEND_TO_RESPONSE + ApiConstants.EQUALS +
                ApiConstants.PARAMS;
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url)
                    .header(ApiConstants.AUTHORIZATION, getBearerToken()).asJson();
            if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return new MovieDetails();
            }
            Gson gson = new Gson();
            Type type = new TypeToken<MovieDetails>() {
            }.getType();
            MovieDetails movieDetails = gson.fromJson(jsonResponse.getBody().toString(), type);
            JSONArray trailers = jsonResponse.getBody().getObject().getJSONObject(ApiConstants.VIDEOS)
                    .getJSONArray(ApiConstants.RESULTS.toLowerCase());
            Type trailer = new TypeToken<List<Trailers>>() {
            }.getType();
            List<Trailers> movieTrailers = gson.fromJson(trailers.toString(), trailer);
            movieDetails.setMovieTrailers(movieTrailers);
            JSONArray images = jsonResponse.getBody().getObject().getJSONObject(ApiConstants.IMAGES)
                    .getJSONArray(ApiConstants.POSTERS);
            Type image = new TypeToken<List<Images>>() {
            }.getType();
            List<Images> movieImages = gson.fromJson(images.toString(), image);
            movieDetails.setMovieImages(movieImages);
            JSONArray casts = jsonResponse.getBody().getObject().getJSONObject(ApiConstants.CREDITS)
                    .getJSONArray(ApiConstants.CAST.toLowerCase());
            JSONArray crews = jsonResponse.getBody().getObject().getJSONObject(ApiConstants.CREDITS)
                    .getJSONArray(ApiConstants.CREW.toLowerCase());
            Type castCrew = new TypeToken<List<Persons>>() {
            }.getType();
            Map<String, List<Persons>> persons = new LinkedHashMap<>();
            persons.put(ApiConstants.CAST.toLowerCase(), gson.fromJson(casts.toString(), castCrew));
            persons.put(ApiConstants.CREW.toLowerCase(), gson.fromJson(crews.toString(), castCrew));
            movieDetails.setPersons(persons);
            return movieDetails;
        } catch (UnirestException e) {
            return new MovieDetails();
        }
    }

    @Override
    public void getStreamingId(String imdbId, String movieName, String movieYear, List<MovieStreaming> movieStreaming) {
        Movies movie = getMovieRepository().findByMovieImdbId(imdbId);
        if (movie == null) {
            List<Movies> SearchMovie = getMovieRepository().findByMovieNameAndMovieReleaseDate(movieName, movieYear);
            movie = SearchMovie.size() == 1 ? SearchMovie.get(0) : new Movies();
        }
        if(StringUtils.hasText(movie.getLookMovieId())) {
            movieStreaming.add(MovieStreaming.builder().server("Look Movies").url("https://lookmovie.plus/?trembed=0&trid="+movie.getLookMovieId()+"&trtype=1").build());
        }
        if (!StringUtils.hasText(movie.getMovieServerIds())) {
            if(StringUtils.hasText(movie.getMovieStreamingId())) {
                List<String> serverIds = getMovieStreamingByMovieId(movie.getMovieStreamingId());
                if (movie.getId() != null) {
                    movie.setMovieServerIds(serverIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
                    movie.setMovieImdbId(imdbId);
                    getMovieRepository().save(movie);
                }
            } else if(StringUtils.hasText(movie.getLookMovieId())) {
                movie.setMovieImdbId(imdbId);
                getMovieRepository().save(movie);
            }
        }
        if (StringUtils.hasText(movie.getMovieServerIds())) {
            List<String> servers = Stream.of(movie.getMovieServerIds().split(",")).collect(Collectors.toList());
            for(int i = 0; i< servers.size(); i++) {
                String url = "https://watchseries.pe/ajax/episode/sources/" + servers.get(i);
                // String url = "https://himovies.to/ajax/episode/sources/" + servers.get(i);
                try {
                    HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
                    if (jsonResponse.getStatus() == HttpStatus.SC_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ServerUrls>() {}.getType();
                        ServerUrls serverUrls = gson.fromJson(jsonResponse.getBody().toString(), type);
                        if(serverUrls != null) {
                            movieStreaming.add(MovieStreaming.builder().server(i == 0 ? "CloudUp": i == 1 ? "MegaUpload": ("MegaUpload" + i)).url(serverUrls.getLink()).build());
                        }
                    }
                } catch (UnirestException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private List<String> getMovieStreamingByMovieId(String movieId) {
        List<String> serverIds = new ArrayList<>();
        String url = "https://watchseries.pe/ajax/episode/list/" + movieId;
        try {
            HttpResponse<String> jsonResponse = Unirest.get(url).asString();
            if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return new ArrayList<>();
            }
            Document doc = Jsoup.parse(jsonResponse.getBody());
            Element body = doc.body();
            Elements elements = body.getElementsByClass("nav-item");
            for (Element element : elements) {
                String serverId = element.select("a").attr("data-linkid");
                serverIds.add(serverId);
                System.out.println(element);
            }

        } catch (UnirestException e) {

        }
        return serverIds;
    }
}
