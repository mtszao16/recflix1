package com.recflix.app;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a filter for searching for movie
 */
public class FeedbackFilter {

    private String id;
    private String userId;
    private String movieId;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("movieId")
    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}