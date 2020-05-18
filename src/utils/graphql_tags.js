import gql from 'graphql-tag';

export const LOG_INTERACTION = gql`
  mutation LogUserInteractionMutation(
    $type: String!
    $movieId: String!
    $amount: Int
  ) {
    logUserInteraction(type: $type, movieId: $movieId, amount: $amount) {
      interactionType
    }
  }
`;

export const SIGNUP_MUTATION = gql`
  mutation CreateUserMutation($authProvider: AuthData!, $name: String!) {
    createUser(authProvider: $authProvider, name: $name) {
      id
    }
  }
`;

export const SIGNIN_MUTATION = gql`
  mutation SigninUserMutation($auth: AuthData) {
    signinUser(auth: $auth) {
      token
    }
  }
`;

export const GET_ALL_MOVIES = gql`
  query GetAllMovies {
    allMovies {
      id
      name
      movieUrl
      imageUrl
      bannerImageUrl
    }
  }
`;

export const GET_FILTERED_MOVIES = gql`
  query GetAllMovies($filter: MovieFilter) {
    allMovies(filter: $filter) {
      id
      name
      movieUrl
      imageUrl
      bannerImageUrl
    }
  }
`;

export const ADD_WATCHED_MOVIE_MUTATION = gql`
  mutation AddWatchedMovieMutation($movieId: String!, $userId: String!) {
    addWatchedMovie(movieId: $movieId, userId: $userId)
  }
`;

export const RECORD_FEEDBACK_MUTATION = gql`
  mutation RecordFeedbackMutation($rating: Int!, $movieId: String) {
    recordFeedback(rating: $rating, movieId: $movieId) {
      createdAt
      id
      rating
      movieId
      userId
    }
  }
`;

export const GET_FILTERED_FEEDBACKS = gql`
  query GetAllFeedbacks($filter: FeedbackFilter) {
    allFeedbacks(filter: $filter) {
      id
      rating
      createdAt
      movieId
      userId
    }
  }
`;

export const GET_ALL_RECOMMENDED_MOVIES = gql`
  query GetAllMoviesRecommendation {
    allMoviesRecommendation {
      id
      name
      movieUrl
      imageUrl
      bannerImageUrl
    }
  }
`;
