package com.recflix.app;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import graphql.schema.DataFetchingEnvironment;

import java.util.List;

public class Query implements GraphQLRootResolver {

    private final UserRepository userRepository;
    private final UserInteractionRepository userInteractionRepository;
    private final MovieRepository movieRepository;
    private final FeedbackRepository feedbackRepository;
    private final MovieRecommendationRepository movieRecommendationRepository;

    public Query(UserRepository userRepository, UserInteractionRepository userInteractionRepository,
            MovieRepository movieRepository, FeedbackRepository feedbackRepository,
            MovieRecommendationRepository movieRecommendationRepository) {
        this.userRepository = userRepository;
        this.userInteractionRepository = userInteractionRepository;
        this.movieRepository = movieRepository;
        this.feedbackRepository = feedbackRepository;
        this.movieRecommendationRepository = movieRecommendationRepository;
    }

    public List<User> allUsers() {
        return userRepository.getAllUsers();
    }

    public List<UserInteraction> allUserInteractions() {
        return userInteractionRepository.getAllInteractions();
    }

    public List<Movie> allMovies(MovieFilter filter) {
        return movieRepository.getAllMovies(filter);
    }

    public List<MovieRecommendation> getAllMoviesRecommendation(DataFetchingEnvironment env) {
        AuthContext context = env.getContext();
        return movieRecommendationRepository.getAllMoviesRecommendation(context.getUser().getId());
    }

    public List<Feedback> allFeedbacks(FeedbackFilter filter) {
        return feedbackRepository.getAllFeedbacks(filter);
    }
}