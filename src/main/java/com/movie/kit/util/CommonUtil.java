package com.movie.kit.util;

import com.movie.kit.constants.ApiConstants;
import com.movie.kit.domain.MovieDetails;
import com.movie.kit.domain.ShowDetails;
import com.movie.kit.domain.Trailers;
import com.movie.kit.mapping.Images;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommonUtil {

    public static Map<String, String> getGenders() {
        Map<String, String> genders = new LinkedHashMap<>();
        genders.put("1", "Female");
        genders.put("2", "Male");
        genders.put("3", "Non-binary");
        return genders;
    }

    public static String convertMovieOrShowTiming(Integer runTime) {
        int hours = runTime / 60;
        int minutes = runTime % 60;
        return hours + "H : " + minutes + "M";
    }

    public static Map<String, Integer> getDepartmentOrders() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("Directing", 0);
        map.put("Writing", 1);
        map.put("Production", 2);
        map.put("Editing", 3);
        map.put("Sound", 4);
        map.put("Art", 5);
        map.put("Camera", 6);
        map.put("Visual Effects", 7);
        map.put("Costume & Make-Up", 8);
        return map;
    }

    public static Map<String, Integer> getJobOrders() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("Director", 0);
        map.put("Writer", 1);
        map.put("Characters", 2);
        map.put("Producer", 3);
        map.put("Executive Producer", 4);
        map.put("Editor", 5);
        map.put("Sound Designer", 6);
        map.put("Sound Re-Recording Mixer", 7);
        map.put("Original Music Composer", 8);
        map.put("Sound Effects Editor", 9);
        map.put("Supervising Sound Editor", 10);
        map.put("Sound Editor", 11);
        map.put("Supervising Art Director", 12);
        map.put("Art Direction", 13);
        map.put("Set Designer", 14);
        map.put("Set Decoration", 15);
        map.put("Assistant Art Director", 16);
        map.put("Set Decoration Buyer", 17);
        map.put("Set Dresser", 18);
        map.put("Director of Photography", 19);
        map.put("Visual Effects", 20);
        map.put("Visual Effects Supervisor", 21);
        map.put("Visual Effects Producer", 22);
        map.put("VFX Artist", 23);
        map.put("Makeup Department Head", 24);
        map.put("Makeup Supervisor", 25);
        map.put("Makeup Designer", 26);
        map.put("Costume Supervisor", 27);
        map.put("Prosthetics", 28);
        map.put("Set Costumer", 29);
        map.put("Makeup Artist", 30);
        map.put("Costume Design", 31);
        map.put("Special Effects Makeup Artist", 32);
        return map;
    }
    public static Map<String, Integer> getTrailerOrders() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put(ApiConstants.TRAILER, 0);
        map.put(ApiConstants.TEASER, 1);
        return map;
    }

    public static List<Trailers> getMovieTrailers(List<Trailers> movieTrailers, List<Images> movieImages, MovieDetails movie) {
        if (movieTrailers != null && movieTrailers.size() > 0) {
            for (int i = 0; i < movieTrailers.size(); i++) {
                if (movieImages.size() >= movieTrailers.size()) {
                    movieTrailers.get(i).setTrailerImage(movieImages.get(i).getFile_path());
                } else {
                    if (movieImages.size() >= 2) {
                        String imagePath = i % 2 == 0 ? movieImages.get(0).getFile_path() : movieImages.get(1).getFile_path();
                        movieTrailers.get(i).setTrailerImage(imagePath);
                    } else {
                        String imagePath = i % 2 == 0 ? movie.getMovieBanner() : movie.getMoviePoster();
                        movieTrailers.get(i).setTrailerImage(imagePath);
                    }
                }
            }
        }
        return movieTrailers;
    }

    public static List<Trailers> getShowTrailers(List<Trailers> showTrailers, List<Images> showImages, ShowDetails show) {
        if (showTrailers != null && showTrailers.size() > 0) {
            for (int i = 0; i < showTrailers.size(); i++) {
                if (showImages.size() >= showTrailers.size()) {
                    showTrailers.get(i).setTrailerImage(showImages.get(i).getFile_path());
                } else {
                    if (showImages.size() >= 2) {
                        String imagePath = i % 2 == 0 ? showImages.get(0).getFile_path() : showImages.get(1).getFile_path();
                        showTrailers.get(i).setTrailerImage(imagePath);
                    } else {
                        String imagePath = i % 2 == 0 ? show.getShowBanner() : show.getShowPoster();
                        showTrailers.get(i).setTrailerImage(imagePath);
                    }
                }
            }
        }
        return showTrailers;
    }

    public static String formatReleaseDate(String relDate) {
        if(StringUtils.hasText(relDate)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = null;
            try {
                releaseDate = simpleDateFormat.parse(relDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, dd yyyy");
            return dateFormat.format(releaseDate);
        } else {
            return null;
        }
    }

    public static String formatReleaseYear(String relDate) {
        if(StringUtils.hasText(relDate)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = null;
            try {
                releaseDate = simpleDateFormat.parse(relDate);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
            return dateFormat.format(releaseDate);
        } else {
            return null;
        }
    }
}
