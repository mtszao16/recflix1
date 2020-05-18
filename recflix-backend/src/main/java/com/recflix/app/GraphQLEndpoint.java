package com.recflix.app;

import com.coxautodev.graphql.tools.SchemaParser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import graphql.servlet.SimpleGraphQLServlet;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.recflix.utils.AuthUtils;

@WebServlet(urlPatterns = "/graphqlApi")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static final UserRepository userRepository;
    private static final UserInteractionRepository userInteractionRepository;
    private static final MovieRepository movieRepository;
    private static final FeedbackRepository feedbackRepository;
    private static final WatchedMovieRepository watchedMovieRepository;
    private static final MovieRecommendationRepository movieRecommendationRepository;

    static {
        MongoDatabase mongo = new MongoClient("mongodb").getDatabase("recflix");
        userRepository = new UserRepository(mongo.getCollection("users"));
        userInteractionRepository = new UserInteractionRepository(mongo.getCollection("userInteractions"));
        movieRepository = new MovieRepository(mongo.getCollection("movies"));
        feedbackRepository = new FeedbackRepository(mongo.getCollection("feedbacks"));
        watchedMovieRepository = new WatchedMovieRepository(mongo.getCollection("users"));
        movieRecommendationRepository = new MovieRecommendationRepository(mongo.getCollection("recommendations"),
                mongo.getCollection("movies"));
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        return SchemaParser.newParser().file("schema.graphqls")
                .resolvers(
                        new Query(userRepository, userInteractionRepository, movieRepository, feedbackRepository,
                                movieRecommendationRepository),
                        new Mutation(userRepository, userInteractionRepository, movieRepository, feedbackRepository),
                        new SigninResolver(), new UserInteractionResolver(userRepository),
                        new FeedbackResolver(userRepository, movieRepository), new UserResolver(watchedMovieRepository))
                .scalars(Scalars.dateTime).build().makeExecutableSchema();
    }

    @Override
    protected GraphQLContext createContext(Optional<HttpServletRequest> request,
            Optional<HttpServletResponse> response) {
        User user = request.map(req -> req.getHeader("Authorization")).filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ", "")).map(AuthUtils::getUserId).map(userRepository::findById)
                .orElse(null);
        return new AuthContext(user, request, response);
    }

    @Override
    protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
        return errors.stream().filter(e -> e instanceof ExceptionWhileDataFetching || super.isClientError(e)).map(
                e -> e instanceof ExceptionWhileDataFetching ? new SanitizedError((ExceptionWhileDataFetching) e) : e)
                .collect(Collectors.toList());
    }
}