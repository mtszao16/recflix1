package com.recflixEngine.app;

import java.util.*;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

import java.lang.Math;

class CollaborativeFiltering {

    public static List<RatingStruc> getMoviesRecommendations(List<RatingStruc> ratingStrucsList,
            FindIterable<Document> movieDocs, FindIterable<Document> userDocs) {

        MongoCursor<Document> csr1 = userDocs.iterator();
        MongoCursor<Document> csr2 = movieDocs.iterator();

        ListIterator<RatingStruc> ltr2 = ratingStrucsList.listIterator();
        ListIterator<RatingStruc> ltr3 = ratingStrucsList.listIterator();

        List<RatingStruc> recommendations = new ArrayList<RatingStruc>();
        while (csr1.hasNext()) {
            Document user = csr1.next();
            String userId = user.get("_id").toString();

            float upperNum = 0;
            float upperDenom = 0;
            float finalRating = 0;

            while (csr2.hasNext()) {
                Document movie = csr2.next();
                String movieId = movie.get("_id").toString();

                while (ltr2.hasNext()) {
                    RatingStruc _Rat = ltr2.next();
                    double num = 0;
                    double denom1 = 0;
                    double denom2 = 0;
                    boolean flag = false;
                    String _userId = _Rat.getUserId();
                    String _movieId = _Rat.getMovieId();
                    double _finalRating = 0;

                    if (_movieId.equalsIgnoreCase(movieId)) {
                        _finalRating = _Rat.getFinalRating();

                        while (ltr3.hasNext()) {
                            RatingStruc nextRat = ltr3.next();
                            if (nextRat.getUserId().equalsIgnoreCase(userId)) {
                                for (RatingStruc e : ratingStrucsList) {
                                    if (e.getUserId().equalsIgnoreCase(_userId)) {
                                        if (e.getMovieId().equalsIgnoreCase(nextRat.getMovieId())) {
                                            flag = true;
                                            num += (double) nextRat.getFinalRating() * e.getFinalRating();
                                            denom1 += (double) nextRat.getFinalRating() * nextRat.getFinalRating();
                                            denom2 += (double) e.getFinalRating() * e.getFinalRating();
                                        }
                                    }
                                }
                            }
                        }
                        // System.out.println(num + " " + denom1 + " " + denom2);
                        ltr3 = ratingStrucsList.listIterator();
                        if (flag) {
                            upperDenom += num / (Math.sqrt(denom1 * denom2));
                            upperNum += _finalRating * num / (Math.sqrt(denom1 * denom2));
                        }
                    }

                    float predrating = 0;

                    if (upperDenom > 0) {
                        predrating = upperNum / upperDenom;
                    } else {
                        predrating = upperNum;
                    }
                    finalRating = predrating;
                }
                ltr2 = ratingStrucsList.listIterator();
                recommendations.add(new RatingStruc(userId, movieId, finalRating));
            }
            csr2.close();
            csr2 = movieDocs.iterator();
        }
        return recommendations;
    }
}