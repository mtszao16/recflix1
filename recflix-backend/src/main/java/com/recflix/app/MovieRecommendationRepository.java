package com.recflix.app;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Manages movie persistence
 */
public class MovieRecommendationRepository {

    private final MongoCollection<Document> recommendations;
    private final MongoCollection<Document> movies;

    public MovieRecommendationRepository(MongoCollection<Document> recommendations, MongoCollection<Document> movies) {
        this.recommendations = recommendations;
        this.movies = movies;
    }

    public List<MovieRecommendation> getAllMoviesRecommendation(String userId) {
        List<MovieRecommendation> allRecommendedMovies = new ArrayList<>();

        FindIterable<Document> recommendedMoviesDocs = recommendations.find(Filters.eq("userId", userId));
        for (Document el : recommendedMoviesDocs) {
            Document movie = movies.find(Filters.eq("_id", new ObjectId(el.getString("movieId")))).first();

            allRecommendedMovies.add(new MovieRecommendation(el.getString("userId"), el.getDouble("rating"),
                    el.getString("movieId"), movie.getString("name"), movie.getString("movieUrl"),
                    movie.getString("imageUrl"), movie.getString("bannerImageUrl"), movie.getInteger("totalDuration")));
        }

        allRecommendedMovies.sort(Comparator.comparingDouble(MovieRecommendation::getRating).reversed());

        return allRecommendedMovies;
    }
}
