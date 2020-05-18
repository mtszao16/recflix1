package com.recflix.app;

import java.util.List;

import com.coxautodev.graphql.tools.GraphQLResolver;

/**
 * Contains User sub-queries
 */
public class UserResolver implements GraphQLResolver<User> {

    private final WatchedMovieRepository watchedMovieRepository;

    UserResolver(WatchedMovieRepository watchedMovieRepository) {
        this.watchedMovieRepository = watchedMovieRepository;
    }

    public List<WatchedMovie> watchedMovies(User user) {
        if (user.getId() == null) {
            return null;
        }
        
        return watchedMovieRepository.getAllWatchedMovies(user.getId().toString());
    }
}