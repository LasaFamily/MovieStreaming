package com.movie.kit.jsoup;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movie.kit.model.Movies;
import com.movie.kit.model.Shows;
import com.movie.kit.repository.MovieRepository;
import com.movie.kit.repository.ShowRepository;
import org.apache.http.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ShowJsoupServiceImpl implements ShowJsoupService {

    @Autowired
    private ShowRepository showRepository;

    @Override
    public List<Shows> getShows(Integer startIndex, Integer endIndex, Integer showNumber) {
        List<Shows> shows = new ArrayList<>();
        for (int i = startIndex; i <= endIndex; i++) {
            try {
                Document doc = Jsoup.connect("https://himovies.to/tv-show?page=" + i)
                        .userAgent("Mozilla/5.0").timeout(10000).validateTLSCertificates(false).get();
                Element body = doc.body();
                List<Shows> dbShows = getShows(body, showNumber);
                shows.addAll(dbShows);
            } catch (Exception e) {
                System.out.println("Error Occurs : " + i + " : " + e.getMessage());
            }
        }
        return shows;
    }

    private List<Shows> getShows(Element body, Integer showNumber) {
        List<Shows> savedShows = new ArrayList<>();
        Elements elements = body.getElementsByClass("flw-item");
        int i = 1;
        for (Element element : elements) {
            if (i <= showNumber) {
                Element innerElement = element.getElementsByClass("film-poster").first();
                String[] showId = innerElement.select("a").attr("href").split("/tv/");
                if (showId.length > 1) {
                    String[] spiltShowId = showId[1].split("-");
                    if (spiltShowId.length > 0) {
                        String streamingId = spiltShowId[spiltShowId.length - 1];
                        Map<String, String> seasons = getShowStreamingByShowId(streamingId);
                        if (seasons.size() > 0) {
                            for (Map.Entry<String, String> entry : seasons.entrySet()) {
                                Map<String, String> episodes = getShowEpisodesBySeasonId(entry.getValue());
                                if (episodes.size() > 0) {
                                    for (Map.Entry<String, String> mapEntry : episodes.entrySet()) {
                                        Shows show = new Shows();
                                        show.setShowName(element.getElementsByClass("film-name").first().text());
                                        show.setShowStreamingId(streamingId);
                                        show.setSeasonNumber(entry.getKey());
                                        show.setSeasonId(entry.getValue());
                                        show.setEpisodeNumber(mapEntry.getKey());
                                        show.setEpisodeId(mapEntry.getValue());
                                        List<Shows> dbShow = showRepository.findByShowNameAndSeasonNumberAndEpisodeNumber(element.getElementsByClass("film-name").first().text(), entry.getKey(), mapEntry.getKey());
                                        if (dbShow.size() == 1) {
                                            show.setId(dbShow.get(0).getId());
                                        }
                                        Shows dbShows = showRepository.save(show);
                                        savedShows.add(dbShows);
                                    }
                                }
                            }
                        }
                    }
                }
                i++;
            }
        }
        return savedShows;
    }

    private Map<String, String> getShowStreamingByShowId(String showId) {
        Map<String, String> seasons = new LinkedHashMap<>();
        String url = "https://watchseries.pe/ajax/season/list/" + showId;
        try {
            HttpResponse<String> jsonResponse = Unirest.get(url).asString();
            if (jsonResponse.getStatus() == HttpStatus.SC_OK) {
                Document doc = Jsoup.parse(jsonResponse.getBody());
                Element body = doc.body();
                Elements elements = body.getElementsByClass("dropdown-item");
                int i = 1;
                for (Element element : elements) {
                    String seasonId = element.select("a").attr("data-id");
                    String[] seasonNumberSplit = element.select("a").text().split(" ");
                    String seasonNumber;
                    if (seasonNumberSplit.length > 1) {
                        seasonNumber = seasonNumberSplit[1];
                    } else {
                        seasonNumber = String.valueOf(i);
                    }
                    seasons.put(seasonNumber, seasonId);
                    i++;
                }
            }
        } catch (UnirestException e) {

        }
        return seasons;
    }

    private Map<String, String> getShowEpisodesBySeasonId(String seasonId) {
        Map<String, String> episodes = new LinkedHashMap<>();
        String url = "https://watchseries.pe/ajax/season/episodes/" + seasonId;
        try {
            HttpResponse<String> jsonResponse = Unirest.get(url).asString();
            if (jsonResponse.getStatus() == HttpStatus.SC_OK) {
                Document doc = Jsoup.parse(jsonResponse.getBody());
                Element body = doc.body();
                Elements elements = body.getElementsByClass("nav-item");
                for (Element element : elements) {
                    String episodeId = element.select("a").attr("data-id");
                    String episodeNumber = element.select("a").attr("title");
                    String[] eps = episodeNumber.split(":");
                    if (eps.length > 0) {
                        String[] epss = eps[0].split(" ");
                        if (epss.length > 1) {
                            episodes.put(epss[1], episodeId);
                        }
                    }
                }
            }
        } catch (UnirestException e) {

        }
        return episodes;
    }


}
