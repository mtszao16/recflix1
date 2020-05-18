package com.recflix.app;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

/**
 * Manages movie persistence
 */
public class MovieRepository {

    private final MongoCollection<Document> movies;

    public MovieRepository(MongoCollection<Document> movies) {
        this.movies = movies;
    }

    public Movie findById(String id) {
        Document doc = movies.find(eq("_id", new ObjectId(id))).first();
        return movie(doc);
    }

    public Movie saveMovie(Movie movie) {
        Document doc = new Document();
        doc.append("name", movie.getName());
        doc.append("movieUrl", movie.getMovieUrl());
        doc.append("imageUrl", movie.getImageUrl());
        doc.append("bannerImageUrl", movie.getBannerImageUrl());
        doc.append("totalDuration", movie.getTotalDuration());
        movies.insertOne(doc);
        return new Movie(doc.get("_id").toString(), movie.getName(), movie.getMovieUrl(), movie.getImageUrl(),
                movie.getBannerImageUrl(), movie.getTotalDuration());
    }

    private Movie movie(Document doc) {
        return new Movie(doc.get("_id").toString(), doc.getString("name"), doc.getString("movieUrl"),
                doc.getString("imageUrl"), doc.getString("bannerImageUrl"), doc.getInteger("totalDuration"));
    }

    public List<Movie> getAllMovies(MovieFilter filter) {
        Optional<Bson> mongoFilter = Optional.ofNullable(filter).map(this::buildFilter);

        List<Movie> allMovies = new ArrayList<>();
        FindIterable<Document> documents = mongoFilter.map(movies::find).orElseGet(movies::find);
        for (Document doc : documents) {
            allMovies.add(movie(doc));
        }
        return allMovies;
    }

    private Bson buildFilter(MovieFilter filter) {
        String idPattern = filter.getId();
        String namePattern = filter.getName();
        Bson idCondition = null;
        Bson nameCondition = null;
        if (idPattern != null && !idPattern.isEmpty()) {
            idCondition = eq("_id", new ObjectId(idPattern));
        }
        if (namePattern != null && !namePattern.isEmpty()) {
            nameCondition = regex("name", ".*" + namePattern + ".*", "i");
        }
        if (idCondition != null && nameCondition != null) {
            return and(idCondition, nameCondition);
        }

        return idCondition != null ? idCondition : nameCondition;
    }
}
