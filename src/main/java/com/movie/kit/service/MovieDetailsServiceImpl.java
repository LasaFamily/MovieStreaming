package com.movie.kit.service;

import com.movie.kit.autowired.AutowiredService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.domain.*;
import com.movie.kit.mapping.Persons;
import com.movie.kit.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieDetailsServiceImpl extends AutowiredService implements MovieDetailsService {

    @Override
    public MovieDetails getMovieDetailsByMovieId(String movieId) {
        com.movie.kit.mapping.MovieDetails movieDetails = getMovieDetailsInterceptorService().getMovieDetailsByMovieId(movieId);
        MovieDetails movieDetail = getMovieDetails(movieDetails);
        if (movieDetails.getRuntime() != null) {
            movieDetail.setMovieDuration(CommonUtil.convertMovieOrShowTiming(movieDetails.getRuntime()));
        }
        movieDetail.setMovieReleaseDate(CommonUtil.formatReleaseDate(movieDetails.getRelease_date()));
        if (movieDetails.getPersons() != null && !movieDetails.getPersons().isEmpty()) {
            String movieDirectors = movieDetails.getPersons().get(ApiConstants.CREW.toLowerCase()).stream()
                    .filter(m -> m.getJob().equalsIgnoreCase("Director"))
                    .map(Persons::getName).collect(Collectors.joining(", "));
            String movieProducers = movieDetails.getPersons().get(ApiConstants.CREW.toLowerCase())
                    .stream().filter(m -> m.getJob().equalsIgnoreCase("Producer"))
                    .map(Persons::getName).collect(Collectors.joining(", "));
            movieDetail.setMovieDirectors(movieDirectors);
            movieDetail.setMovieProducers(movieProducers);
        }

        if (movieDetails.getPersons() != null && !movieDetails.getPersons().isEmpty()) {
            Map<String, Integer> departmentOrders = CommonUtil.getDepartmentOrders();
            Map<String, Integer> jobOrders = CommonUtil.getJobOrders();
            List<CastCrew> movieCasts = new ArrayList<>(movieDetails.getPersons()
                    .get(ApiConstants.CAST.toLowerCase()).stream()
                    .sorted(Comparator.comparing(Persons::getOrder))
                    .collect(Collectors.groupingBy(Persons::getId, LinkedHashMap::new,
                            Collectors.collectingAndThen(Collectors.toList(), list -> {
                                Optional<CastCrew> castCrew = list.stream().map(person -> new CastCrew(person.getId(),
                                        person.getName(), person.getProfile_path(), list.stream()
                                        .map(Persons::getCharacter)
                                        .collect(Collectors.joining(", ")))).findFirst();
                                return castCrew.orElseGet(CastCrew::new);
                            }))).values());
            List<CastCrew> movieCrews = new ArrayList<>(movieDetails.getPersons()
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
            movieDetail.setMovieCasts(movieCasts);
            movieDetail.setMovieCrews(movieCrews);
        }
        movieDetail.setMovieTrailers(getMovieTrailers(movieDetails));
        List<MovieStreaming> movieStreaming = new ArrayList<>();
        getMovieDetailsInterceptorService().getStreamingId(movieDetail.getMovieId(), movieDetail.getMovieName(),
                CommonUtil.formatReleaseYear(movieDetails.getRelease_date()), movieStreaming);

        movieStreaming.add(MovieStreaming.builder().server("vidsrc").url("https://vidsrc.to/embed/movie/" + movieDetail.getMovieImdbId()).build());
        movieStreaming.add(MovieStreaming.builder().server("2embed").url("https://2embed.pro/embed/movie/" + movieDetail.getMovieImdbId()).build());
        movieStreaming.add(MovieStreaming.builder().server("smashystream").url("https://player.smashy.stream/movie/" + movieDetail.getMovieId()).build());
        movieStreaming.add(MovieStreaming.builder().server("multiembed").url("https://multiembed.mov/?video_id=" + movieDetail.getMovieImdbId()).build());
        movieStreaming.add(MovieStreaming.builder().server("moviesapi").url("https://moviesapi.club/movie/" + movieDetail.getMovieImdbId()).build());
        movieStreaming.add(MovieStreaming.builder().server("primewire").url("https://www.primewire.tf/embed/movie?imdb=" + movieDetail.getMovieImdbId()).build());
        movieStreaming.add(MovieStreaming.builder().server("traze").url("https://enterp.online/embed/" + movieDetail.getMovieImdbId()).build());
        movieDetail.setMovieStreaming(movieStreaming);
        return movieDetail;
    }

    //https://vidsrc.to/embed/movie/tt19637052
    //https://123videos.4u.ms/?imdb_id=tt14539740&server=multi2
    //https://vidsrc.xyz/embed/movie?imdb=
    //https://getsuperembed.link/?video_id=

    // https://embed.smashystream.com/playere.php?tmdb=693134
    // https://vidsrc.me/embed/movie?tmdb=1011985&color=2986cc
    // https://embed.smashystream.com/playere.php?tmdb=1011985
    // https://www.2embed.cc/embed/1011985
    // https://lookmovie.plus/?trembed=0&trid=100&trtype=1
    // https://meserver.buzz/embed/tt14539740
    // https://flixhq.click/wp-content/plugins/fmovie-core/player/player.php?video_id=
    // https://multiembed.mov/directstream.php?video_id=tt14539740
    // https://enterp.online/embed/tt14539740
    // https://1movietv.com/tt29114029
    // https://blackvid.space/embed?tmdb=872585



    private MovieDetails getMovieDetails(com.movie.kit.mapping.MovieDetails movieDetails) {
        MovieDetails movieDetail = MovieDetails.builder()
                .movieId(movieDetails.getId())
                .movieName(movieDetails.getTitle())
                .movieOriginalName(movieDetails.getOriginal_title())
                .movieTagName(movieDetails.getTagline())
                .movieReleaseDate(movieDetails.getRelease_date())
                .movieRating(Double.parseDouble(String.valueOf(BigDecimal
                        .valueOf(Double.parseDouble(movieDetails.getVote_average()))
                        .setScale(1, RoundingMode.UP))))
                .movieBanner(movieDetails.getBackdrop_path())
                .moviePoster(movieDetails.getPoster_path())
                .moviePopularity(movieDetails.getPopularity())
                .genres(movieDetails.getGenres().stream().map(genres ->
                        new Genres(genres.getId(), genres.getName())).collect(Collectors.toList()))
                .movieOverview(movieDetails.getOverview())
                .movieBudget(movieDetails.getBudget())
                .movieCollection(movieDetails.getRevenue())
                .movieImdbId(movieDetails.getImdb_id())
                .movieOriginalLanguage(movieDetails.getOriginal_language())
                .movieStatus(movieDetails.getStatus())
                .build();
        return movieDetail;
    }

    private static List<Trailers> getMovieTrailers(com.movie.kit.mapping.MovieDetails movieDetails) {
        if (movieDetails.getMovieTrailers() != null && !movieDetails.getMovieTrailers().isEmpty()) {
            Map<String, Integer> trailerOrder = CommonUtil.getTrailerOrders();
            List<Trailers> movieTrailers = movieDetails.getMovieTrailers().stream()
                    .map(movieTrailer -> {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ssXX");
                        try {
                            movieTrailer.setTrailerDate(simpleDateFormat.parse(movieTrailer.getPublished_at()));
                        } catch (ParseException e) {
                        }
                        return movieTrailer;
                    })
                    .map(movieTrailer -> new Trailers(movieTrailer.getName(), movieTrailer.getKey(),
                            movieTrailer.getType(), movieTrailer.getTrailerDate()))
                    .sorted(Comparator.comparing(Trailers::getTrailerDate).reversed())
                    .sorted(Comparator.comparing(movieTrailer ->
                            trailerOrder.get(movieTrailer.getTrailerType()) == null ? 2 :
                                    trailerOrder.get(movieTrailer.getTrailerType())))
                    .collect(Collectors.toList());
            return CommonUtil.getMovieTrailers(movieTrailers, movieDetails.getMovieImages(),
                    MovieDetails.builder().movieBanner(movieDetails.getBackdrop_path())
                            .moviePoster(movieDetails.getPoster_path()).build());
        } else {
            return new ArrayList<>();
        }
    }
}
