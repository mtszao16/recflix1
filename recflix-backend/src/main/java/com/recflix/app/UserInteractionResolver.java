package com.recflix.app;

import com.coxautodev.graphql.tools.GraphQLResolver;

/**
 * Contains UserInteraction sub-queries
 */
public class UserInteractionResolver implements GraphQLResolver<UserInteraction> {

    private final UserRepository userRepository;

    UserInteractionResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User interactedBy(UserInteraction userInteraction) {
        if (userInteraction.getUserId() == null) {
            return null;
        }
        return userRepository.findById(userInteraction.getUserId());
    }
}