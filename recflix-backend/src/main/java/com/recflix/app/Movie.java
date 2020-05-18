package com.recflix.app;

/**
 * Represents a movie of the system
 */
public class Movie {

    private final String id;
    private final String name;
    private final String movieUrl;
    private final String imageUrl;
    private final String bannerImageUrl;
    private final Integer totalDuration;

    public Movie(String name, String movieUrl, String imageUrl, String bannerImageUrl, Integer totalDuration) {
        this(null, name, movieUrl, imageUrl, bannerImageUrl, totalDuration);
    }

    public Movie(String id, String name, String movieUrl, String imageUrl, String bannerImageUrl,
            Integer totalDuration) {
        this.id = id;
        this.name = name;
        this.movieUrl = movieUrl;
        this.imageUrl = imageUrl;
        this.bannerImageUrl = bannerImageUrl;
        this.totalDuration = totalDuration;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMovieUrl() {
        return movieUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }
}
