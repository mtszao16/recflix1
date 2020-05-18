package com.recflix.app;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a filter for searching for movie
 */
public class MovieFilter {

    private String id;
    private String name;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}