package com.recflixEngine.app;

/**
 * Represents a RatingStruc of the engine
 */
public class RatingStruc {

    private final String userId;
    private final String movieId;
    private final double finalRating;

    public RatingStruc(String userId, String movieId, double finalRating) {
        this.userId = userId;
        this.movieId = movieId;
        this.finalRating = finalRating;
    }

    public String getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public double getFinalRating() {
        return finalRating;
    }
}
