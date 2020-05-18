package com.recflixEngine.app;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import com.mongodb.MongoClient;

import java.util.ArrayList;
import java.util.List;

public class App {
    static MongoCollection<Document> movies;
    static MongoCollection<Document> users;

    static {
        MongoDatabase mongo = new MongoClient().getDatabase("recflix");
        movies = mongo.getCollection("movies");
        users = mongo.getCollection("users");
        new DBUtils(mongo.getCollection("userInteractions"), mongo.getCollection("users"),
                mongo.getCollection("movies"), mongo.getCollection("feedbacks"),
                mongo.getCollection("recommendations"));
    }

    public static void main(String[] args) {
        List<WeightActions> wts = DBUtils.getWeightedActions();
        List<RatingStruc> rats = new ArrayList<RatingStruc>();
        for (WeightActions wt : wts) {
            Uarca uarca = new Uarca(wt);
            double finalRating = uarca.getFinalRating();
            DBUtils.saveFinalRatingsToDb(wt.getUserId(), wt.getMovieId(), finalRating);
            rats.add(new RatingStruc(wt.getUserId(), wt.getMovieId(), finalRating));
            /*
             * System.out.println(wt.getExplicitRating() + " " + wt.getRecomdToUserWt() +
             * " " + wt.getAddFavWt() + " " + wt.getWatchLstWt() + " " + wt.getRemFavWt() +
             * " " + wt.getTimeSpendWt() + " " + wt.getBckCntrlWt() + " " +
             * wt.getFwdCntrlWt() + " " + wt.getBckSeekWt() + " " + wt.getFwdSeekWt() + " "
             * + wt.getViewWt());
             */
        }

        rats = CollaborativeFiltering.getMoviesRecommendations(rats, movies.find(), users.find());
        for (RatingStruc e : rats) {
            DBUtils.storeRecommendationsToDb(e.getUserId(), e.getMovieId(), e.getFinalRating());

            // System.out.println(e.getUserId() + " " + e.getMovieId() + " " +
            // e.getFinalRating());
        }
    }
}