package com.movie.kit.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.movie.kit.autowired.AutowiredService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.domain.*;
import com.movie.kit.mapping.Persons;
import com.movie.kit.mapping.ServerUrls;
import com.movie.kit.model.Movies;
import com.movie.kit.model.Shows;
import com.movie.kit.util.CommonUtil;
import org.apache.http.HttpStatus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ShowDetailsServiceImpl extends AutowiredService implements ShowDetailsService {
    @Override
    public ShowDetails getShowDetailsByShowId(String showId) {
        com.movie.kit.mapping.ShowDetails showDetails = getShowDetailsInterceptorService().getShowDetailsByShowId(showId);
        ShowDetails showDetail = getShowDetails(showDetails);
        if (!showDetails.getEpisode_run_time().isEmpty() || StringUtils.hasText(showDetails.getRuntime())) {
            showDetail.setShowDuration(CommonUtil.convertMovieOrShowTiming(StringUtils.hasText(showDetails.getRuntime()) ? Integer.parseInt(showDetails.getRuntime()) : showDetails.getEpisode_run_time().get(0)));
        }
        if (showDetails.getPersons() != null && !showDetails.getPersons().isEmpty()) {
            String showDirectors = showDetails.getPersons().get(ApiConstants.CREW.toLowerCase()).stream()
                    .filter(m -> m.getJob().equalsIgnoreCase("Director"))
                    .map(Persons::getName).collect(Collectors.joining(", "));
            String showProducers = showDetails.getPersons().get(ApiConstants.CREW.toLowerCase()).stream()
                    .filter(m -> m.getJob().equalsIgnoreCase("Producer"))
                    .map(Persons::getName).collect(Collectors.joining(", "));
            showDetail.setShowDirectors(StringUtils.hasText(showDirectors) ? showDirectors : "N/A");
            showDetail.setShowProducers(StringUtils.hasText(showProducers) ? showProducers : "N/A");
        }

        if (showDetails.getPersons() != null && !showDetails.getPersons().isEmpty()) {
            Map<String, Integer> departmentOrders = CommonUtil.getDepartmentOrders();
            Map<String, Integer> jobOrders = CommonUtil.getJobOrders();
            List<CastCrew> showCasts = new ArrayList<>(showDetails.getPersons()
                    .get(ApiConstants.CAST.toLowerCase()).stream()
                    .sorted(Comparator.comparing(Persons::getOrder))
                    .collect(Collectors.groupingBy(Persons::getId, LinkedHashMap::new,
                            Collectors.collectingAndThen(Collectors.toList(), list -> {
                                Optional<CastCrew> castCrew = list.stream().map(person ->
                                        new CastCrew(person.getId(), person.getName(), person.getProfile_path(),
                                                list.stream()
                                                        .map(Persons::getCharacter)
                                                        .collect(Collectors.joining(", ")))).findFirst();
                                return castCrew.orElseGet(CastCrew::new);
                            }))).values());
            List<CastCrew> showCrews = new ArrayList<>(showDetails.getPersons()
                    .get(ApiConstants.CREW.toLowerCase()).stream()
                    .sorted(Comparator.comparing(person -> departmentOrders.get(person.getDepartment()) == null ? 9 :
                            departmentOrders.get(person.getDepartment())))
                    .sorted(Comparator.comparing(person -> jobOrders.get(person.getJob()) == null ? 33 :
                            jobOrders.get(person.getJob())))
                    .collect(Collectors.groupingBy(Persons::getId, LinkedHashMap::new,
                            Collectors.collectingAndThen(Collectors.toList(), list -> {
                                Optional<CastCrew> castCrew = list.stream().map(person -> new CastCrew(person.getId(),
                                        person.getName(), person.getProfile_path(), list.stream()
                                        .map(Persons::getJob)
                                        .collect(Collectors.joining(", ")))).findFirst();
                                return castCrew.orElseGet(CastCrew::new);
                            }))).values());
            showDetail.setShowCasts(showCasts);
            showDetail.setShowCrews(showCrews);
        }
        showDetail.setShowTrailers(getShowTrailers(showDetails));
        List<Seasons> seasons = showDetails.getSeasons().stream()
                .filter(season -> !season.getName().equals("Specials"))
                .map(season -> new Seasons(season.getId(), season.getName(), season.getSeason_number(),
                        CommonUtil.formatReleaseDate(season.getAir_date()), season.getPoster_path(), String.valueOf(BigDecimal
                        .valueOf(season.getVote_average())
                        .setScale(1, RoundingMode.UP))))
                .sorted(Comparator.comparingInt(Seasons::getSeasonNumber).reversed()).collect(Collectors.toList());
        showDetail.setSeasons(seasons);
        return showDetail;
    }

    @Override
    public List<Episodes> getEpisodesByShowIdAndSeasonId(String showId, Integer seasonNumber) {
        List<com.movie.kit.mapping.Episodes> dbEpisodes = getShowDetailsInterceptorService().getEpisodesByShowIdAndSeasonId(showId, seasonNumber);
        if(dbEpisodes != null && dbEpisodes.size() > 0) {
            return dbEpisodes.stream()
                    .map(episode ->
                            new Episodes(episode.getId(), episode.getName(), episode.getEpisode_number(), episode.getStill_path(),
                            CommonUtil.convertMovieOrShowTiming(episode.getRuntime() == null ? 0 : episode.getRuntime()),
                            String.valueOf(BigDecimal
                                    .valueOf(episode.getVote_average())
                                    .setScale(1, RoundingMode.UP))))
                    .sorted(Comparator.comparingInt(Episodes::getEpisodeNumber)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getShowStreamingId(String showId, String seasonNumber, String episodeNumber) {
        List<String> serverData = new ArrayList<>();
        com.movie.kit.mapping.ShowDetails showDetails = getShowDetailsInterceptorService().getShowDetailsByShowId(showId);
        List<Shows> SearchShow = getShowRepository().findByShowNameAndSeasonNumberAndEpisodeNumber(showDetails.getName(), seasonNumber, episodeNumber);
        Shows show = SearchShow.size() > 0 ? SearchShow.get(0) : new Shows();
        if (StringUtils.hasText(show.getEpisodeId())) {
            List<String> serverIds = getShowStreamingByEpisodeId(show.getEpisodeId());
            if (show.getId() != null) {
                show.setShowServerIds(serverIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
                show.setShowImdbId(showDetails.getImdb_id());
                getShowRepository().save(show);
            }
        }


        if (StringUtils.hasText(show.getShowServerIds())) {
            List<String> servers = Stream.of(show.getShowServerIds().split(",")).collect(Collectors.toList());
            for(int i = 0; i< servers.size(); i++) {
                // String url = "https://watchseries.pe/ajax/episode/sources/" + servers.get(i);
                String url = "https://himovies.to/ajax/episode/sources/" + servers.get(i);
                try {
                    HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
                    if (jsonResponse.getStatus() == HttpStatus.SC_OK) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<ServerUrls>() {}.getType();
                        ServerUrls serverUrls = gson.fromJson(jsonResponse.getBody().toString(), type);
                        if(serverUrls != null) {
                            serverData.add(serverUrls.getLink());
                        }
                    }
                } catch (UnirestException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return serverData;
    }

    private List<String> getShowStreamingByEpisodeId(String episodeId) {
        List<String> serverIds = new ArrayList<>();
        // String url = "https://watchseries.pe/ajax/episode/servers/" + episodeId;
        String url = "https://himovies.to/ajax/episode/servers/" + episodeId;
        try {
            HttpResponse<String> jsonResponse = Unirest.get(url).asString();
            if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
                return new ArrayList<>();
            }
            Document doc = Jsoup.parse(jsonResponse.getBody());
            Element body = doc.body();
            Elements elements = body.getElementsByClass("nav-item");
            for (Element element : elements) {
                String serverId = element.select("a").attr("data-id");
                serverIds.add(serverId);
                System.out.println(element);
            }

        } catch (UnirestException e) {

        }
        return serverIds;
    }
    private static List<Trailers> getShowTrailers(com.movie.kit.mapping.ShowDetails showDetails) {
        if (showDetails.getShowTrailers() != null && !showDetails.getShowTrailers().isEmpty()) {
            Map<String, Integer> trailerOrder = CommonUtil.getTrailerOrders();
            List<Trailers> showTrailers = showDetails.getShowTrailers().stream()
                    .map(showTrailer -> {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ssXX");
                        try {
                            showTrailer.setTrailerDate(simpleDateFormat.parse(showTrailer.getPublished_at()));
                        } catch (ParseException e) {
                        }
                        return showTrailer;
                    })
                    .map(showTrailer -> new Trailers(showTrailer.getName(), showTrailer.getKey(),
                            showTrailer.getType(), showTrailer.getTrailerDate()))
                    .sorted(Comparator.comparing(Trailers::getTrailerDate).reversed())
                    .sorted(Comparator.comparing(showTrailer ->
                            trailerOrder.get(showTrailer.getTrailerType()) == null ? 2 :
                                    trailerOrder.get(showTrailer.getTrailerType())))
                    .collect(Collectors.toList());
            return CommonUtil.getShowTrailers(showTrailers, showDetails.getShowImages(),
                    ShowDetails.builder().showBanner(showDetails.getBackdrop_path())
                            .showPoster(showDetails.getPoster_path()).build());
        } else {
            return new ArrayList<>();
        }
    }

    private ShowDetails getShowDetails(com.movie.kit.mapping.ShowDetails showDetails) {
        ShowDetails showDetail = ShowDetails.builder()
                .showId(showDetails.getId())
                .showName(showDetails.getName())
                .showOriginalName(showDetails.getOriginal_name())
                .showTagName(showDetails.getTagline())
                .firstReleaseDate(CommonUtil.formatReleaseDate(showDetails.getFirst_air_date()))
                .lastReleaseDate(CommonUtil.formatReleaseDate(showDetails.getLast_air_date()))
                .showRating(String.valueOf(BigDecimal
                        .valueOf(Double.parseDouble(showDetails.getVote_average()))
                        .setScale(1, RoundingMode.UP)))
                .showBanner(showDetails.getBackdrop_path())
                .showPoster(showDetails.getPoster_path())
                .showPopularity(showDetails.getPopularity())
                .genres(showDetails.getGenres().stream().map(genres ->
                        new Genres(genres.getId(), genres.getName())).collect(Collectors.toList()))
                .showOverview(showDetails.getOverview())
                .numberOfSeasons(Integer.parseInt(showDetails.getNumber_of_seasons()) <= 9 ? "0" + showDetails.getNumber_of_seasons() : showDetails.getNumber_of_seasons())
                .numberOfEpisodes(Integer.parseInt(showDetails.getNumber_of_episodes()) <= 9 ? "0" + showDetails.getNumber_of_episodes() : showDetails.getNumber_of_episodes())
                .showImdbId(showDetails.getImdb_id())
                .showOriginalLanguage(showDetails.getOriginal_language())
                .showStatus(showDetails.getStatus())
                .build();
        return showDetail;
    }
}
