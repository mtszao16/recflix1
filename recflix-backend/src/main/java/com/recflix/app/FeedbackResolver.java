package com.recflix.app;

import com.coxautodev.graphql.tools.GraphQLResolver;

/**
 * Contains Feedback sub-queries
 */
public class FeedbackResolver implements GraphQLResolver<Feedback> {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    FeedbackResolver(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public User watchedBy(Feedback feedback) {
        if (feedback.getUserId() == null) {
            return null;
        }
        return userRepository.findById(feedback.getUserId());
    }

    public Movie watchedMovie(Feedback feedback) {
        if (feedback.getMovieId() == null) {
            return null;
        }
        return movieRepository.findById(feedback.getMovieId());
    }
}