package com.recflix.app;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Projections.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Manages movie persistence
 */
public class WatchedMovieRepository {

    private final MongoCollection<Document> users;

    public WatchedMovieRepository(MongoCollection<Document> users) {
        this.users = users;
    }

    public List<WatchedMovie> getAllWatchedMovies(String userId) {
        List<WatchedMovie> watchedMovies = new ArrayList<WatchedMovie>();

        List<Document> watchedMoviesDocs = (List<Document>) users.find(Filters.eq("_id", new ObjectId(userId)))
                .projection(fields(include("watchedMovies"))).map(document -> document.get("watchedMovies")).first();

        if (watchedMoviesDocs == null) {
            return new ArrayList<WatchedMovie>();
        }

        for (Document el : watchedMoviesDocs) {
            watchedMovies.add(new WatchedMovie(el.get("_id").toString(), el.getString("name"), el.getString("movieUrl"),
                    el.getString("imageUrl"), el.getString("bannerImageUrl"), el.getInteger("totalDuration"),
                    el.getInteger("watchedCount"), el.getInteger("watchedDuration")));
        }

        return watchedMovies;
    }
}
