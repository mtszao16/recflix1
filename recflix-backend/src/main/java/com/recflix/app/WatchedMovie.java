package com.recflix.app;

/**
 * Represents a watched movie of the system
 */
public class WatchedMovie extends Movie {

    private final Integer watchedCount;
    private final Integer watchedDuration;

    public WatchedMovie(String name, String movieUrl, String imageUrl, String bannerImageUrl, Integer totalDuration,
            Integer watchedCount, Integer watchedDuration) {
        this(null, name, movieUrl, imageUrl, bannerImageUrl, totalDuration, watchedCount, watchedDuration);
    }

    public WatchedMovie(String id, String name, String movieUrl, String imageUrl, String bannerImageUrl,
            Integer totalDuration, Integer watchedCount, Integer watchedDuration) {
        super(id, name, movieUrl, imageUrl, bannerImageUrl, totalDuration);
        this.watchedCount = watchedCount;
        this.watchedDuration = watchedDuration;
    }

    public Integer getWatchedCount() {
        return watchedCount;
    }

    public Integer getWatchedDuration() {
        return watchedDuration;
    }
}
