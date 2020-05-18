package com.recflix.app;

/**
 * Represents a watched movie of the system
 */
public class MovieRecommendation extends Movie {

    private final String userId;
    private final Double rating;

    public MovieRecommendation(String userId, Double rating, String name, String movieUrl, String imageUrl,
            String bannerImageUrl, Integer totalDuration) {
        this(userId, rating, null, name, movieUrl, imageUrl, bannerImageUrl, totalDuration);
    }

    public MovieRecommendation(String userId, Double rating, String movieId, String name, String movieUrl,
            String imageUrl, String bannerImageUrl, Integer totalDuration) {
        super(movieId, name, movieUrl, imageUrl, bannerImageUrl, totalDuration);
        this.userId = userId;
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public Double getRating() {
        return rating;
    }

}
