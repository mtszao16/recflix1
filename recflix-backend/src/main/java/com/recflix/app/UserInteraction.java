package com.recflix.app;

import java.time.ZonedDateTime;

public class UserInteraction {

    private final String id;
    private final ZonedDateTime interactionTime;
    private final String interactionType;
    private final String userId;
    private final String movieId;
    private final Double value;
    private final Integer amount;

    public UserInteraction(ZonedDateTime interactionTime, String interactionType, String userId, String movieId,
            Double value, Integer amount) {
        this(null, interactionTime, interactionType, userId, movieId, value, amount);
    }

    public UserInteraction(String id, ZonedDateTime interactionTime, String interactionType, String userId,
            String movieId, Double value, Integer amount) {
        this.id = id;
        this.interactionTime = interactionTime;
        this.interactionType = interactionType;
        this.userId = userId;
        this.movieId = movieId;
        this.value = value;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getInteractionTime() {
        return interactionTime;
    }

    public String getInteractionType() {
        return interactionType;
    }

    public String getUserId() {
        return userId;
    }

    public String getMovieId() {
        return movieId;
    }

    public Double getValue() {
        return value;
    }

    public Integer getAmount() {
        return amount;
    }
}