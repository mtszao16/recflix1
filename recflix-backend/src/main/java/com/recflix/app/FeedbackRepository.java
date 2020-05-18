package com.recflix.app;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;

/**
 * Manages Feedback persistence
 */
public class FeedbackRepository {

    private final MongoCollection<Document> feedbacks;

    FeedbackRepository(MongoCollection<Document> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Feedback findById(String id) {
        Document doc = feedbacks.find(eq("_id", new ObjectId(id))).first();
        return feedback(doc);
    }

    public List<Feedback> getAllFeedbacks(FeedbackFilter filter) {
        Optional<Bson> mongoFilter = Optional.ofNullable(filter).map(this::buildFilter);
        List<Feedback> allFeedbacks = new ArrayList<>();

        FindIterable<Document> documents = mongoFilter.map(feedbacks::find).orElseGet(feedbacks::find);

        for (Document doc : documents) {
            allFeedbacks.add(feedback(doc));
        }
        return allFeedbacks;
    }

    public Feedback saveFeedback(Feedback feedback) {
        FindIterable<Document> docs = feedbacks
                .find(and(eq("userId", feedback.getUserId()), eq("movieId", feedback.getMovieId())));

        Document doc = new Document();
        Integer docsCount = 0;
        for (Document el : docs) {
            docsCount += 1;
        }

        if (docsCount < 1) {
            doc.append("rating", feedback.getRating());
            doc.append("finalRating", feedback.getFinalRating());
            doc.append("userId", feedback.getUserId());
            doc.append("movieId", feedback.getMovieId());
            doc.append("createdAt", Scalars.dateTime.getCoercing().serialize(feedback.getCreatedAt()));
            feedbacks.insertOne(doc);
        } else {
            doc = feedbacks.findOneAndUpdate(
                    and(eq("userId", feedback.getUserId()), eq("movieId", feedback.getMovieId())),
                    Updates.set("rating", feedback.getRating()));
            return new Feedback(doc.get("_id").toString(), doc.getInteger("rating"), doc.getDouble("finalRating"),
                    ZonedDateTime.parse(doc.getString("createdAt")), doc.getString("userId"), doc.getString("movieId"));
        }

        return new Feedback(doc.get("_id").toString(), doc.getInteger("rating"), doc.getDouble("finalRating"),
                ZonedDateTime.parse(doc.getString("createdAt")), doc.getString("userId"), doc.getString("movieId"));
    }

    private Feedback feedback(Document doc) {
        return new Feedback(doc.get("_id").toString(), doc.getInteger("rating"), doc.getDouble("finalRating"),
                ZonedDateTime.parse(doc.getString("createdAt")), doc.getString("userId"), doc.getString("movieId"));
    }

    private Bson buildFilter(FeedbackFilter filter) {
        String idPattern = filter.getId();
        String movieIdPattern = filter.getMovieId();
        String userIdPattern = filter.getUserId();
        Bson idCondition = null;
        Bson movieIdCondition = null;
        Bson userIdCondition = null;

        if (idPattern != null && !idPattern.isEmpty()) {
            idCondition = eq("_id", new ObjectId(idPattern));
        }

        if (movieIdPattern != null && !movieIdPattern.isEmpty()) {
            movieIdCondition = eq("movieId", movieIdPattern);
        }

        if (userIdPattern != null && !userIdPattern.isEmpty()) {
            userIdCondition = eq("userId", userIdPattern);
        }

        if (idCondition != null && movieIdCondition != null && userIdCondition != null) {
            return and(idCondition, movieIdCondition, userIdCondition);
        }

        if (idCondition != null && movieIdCondition != null && userIdCondition == null) {
            return and(idCondition, movieIdCondition);
        }

        if (idCondition != null && movieIdCondition == null && userIdCondition != null) {
            return and(idCondition, userIdCondition);
        }

        if (idCondition == null && movieIdCondition != null && userIdCondition != null) {
            return and(movieIdCondition, userIdCondition);
        }

        return idCondition != null ? idCondition : userIdCondition;
    }
}
