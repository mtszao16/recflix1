package com.recflix.app;

import com.recflix.utils.HashString;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Manages user persistence
 */
public class UserRepository {

    private final MongoCollection<Document> users;

    public UserRepository(MongoCollection<Document> users) {
        this.users = users;
    }

    public User findByEmail(String email) {
        Document doc = users.find(eq("email", email)).first();
        return user(doc);
    }

    public User findById(String id) {
        Document doc = users.find(eq("_id", new ObjectId(id))).first();
        return user(doc);
    }

    public User saveUser(User user) {
        String hashedPassword = HashString.hashTheString(user.getPassword());
        Document doc = new Document();
        doc.append("name", user.getName());
        doc.append("email", user.getEmail());
        doc.append("password", hashedPassword);
        doc.append("watchedMovies", new ArrayList<WatchedMovie>());
        users.insertOne(doc);
        return new User(doc.get("_id").toString(), user.getName(), user.getEmail(), hashedPassword);
    }

    public void saveWatchedMovie(Movie movie, String userId) {
        List<Document> watchedMoviesDocs = (List<Document>) users.find(eq("_id", new ObjectId(userId)))
                .projection(fields(include("watchedMovies"))).map(document -> document.get("watchedMovies")).first();

        Document movieSpec = new Document();
        Boolean movieAlreadyWatched = false;

        for (Document el : watchedMoviesDocs) {
            if (new String(el.get("_id").toString()).equals(movie.getId().toString())) {
                movieAlreadyWatched = true;
                el.put("watchedCount", el.getInteger("watchedCount") + 1);
                el.put("watchedDuration", el.getInteger("watchedDuration") + 1);
                break;
            }
        }

        if (!movieAlreadyWatched) {
            movieSpec.append("_id", movie.getId());
            movieSpec.append("name", movie.getName());
            movieSpec.append("totalDuration", movie.getTotalDuration());
            movieSpec.append("movieUrl", movie.getMovieUrl());
            movieSpec.append("imageUrl", movie.getImageUrl());
            movieSpec.append("bannerImageUrl", movie.getBannerImageUrl());
            movieSpec.append("watchedCount", 1);
            movieSpec.append("watchedDuration", 1);
            users.updateOne(eq("_id", new ObjectId(userId)), Updates.push("watchedMovies", movieSpec));
        } else {
            users.updateOne(eq("_id", new ObjectId(userId)), Updates.set("watchedMovies", watchedMoviesDocs));
        }
    }

    private User user(Document doc) {
        return new User(doc.get("_id").toString(), doc.getString("name"), doc.getString("email"),
                doc.getString("password"));
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        for (Document doc : users.find()) {
            allUsers.add(user(doc));
        }
        return allUsers;
    }
}
