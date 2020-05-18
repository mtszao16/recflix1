import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { graphql, compose } from 'react-apollo';
import PropTypes from 'prop-types';

import {
  GET_ALL_MOVIES,
  ADD_WATCHED_MOVIE_MUTATION,
  GET_ALL_RECOMMENDED_MOVIES
} from '../utils/graphql_tags';
import { getUserId } from '../utils/jwtutils';

class Landing extends Component {
  static propTypes = {
    getAllMovies: PropTypes.shape({
      loading: PropTypes.bool,
      error: PropTypes.object,
      allMovies: PropTypes.array
    }).isRequired,
    getAllMoviesRecommendation: PropTypes.shape({
      loading: PropTypes.bool,
      error: PropTypes.object,
      allMoviesRecommendation: PropTypes.array
    }).isRequired
  };

  handleOnClick = async movie => {
    await this.props.addWatchedMovie({
      variables: {
        movieId: movie.id,
        userId: getUserId()
      }
    });
    this.props.history.push(`/movie/${movie.id}`);
  };

  render() {
    const {
      getAllMovies: { allMovies },
      getAllMoviesRecommendation: { allMoviesRecommendation }
    } = this.props;
    return (
      <div className="main-bg">
        {/* <div className="main-bg">
          <img src="https://raw.githubusercontent.com/UtkarshGupta-CS/recflix/master/public/assets/images/bgimage.jpg" />
        </div> */}
        {allMoviesRecommendation &&
          allMoviesRecommendation.length > 0 && (
            <div
              id="recommendationIndicators"
              className="carousel slide"
              data-ride="carousel"
              style={{ padding: '30px', width: '80%', margin: '0 auto' }}
            >
              <div className="carousel-inner">
                {allMoviesRecommendation.map((movie, index) => {
                  if (index < 5)
                    return (
                      <div
                        key={index}
                        className={`carousel-item ${
                          index === 0 ? 'active' : ''
                        }`}
                        onClick={() => this.handleOnClick(movie)}
                      >
                        <div className="card-body">
                          <img
                            className="d-block w-100"
                            src={movie.bannerImageUrl || ''}
                            alt="Slide"
                            height="400"
                          />
                        </div>
                      </div>
                    );
                })}
              </div>
              <a
                className="carousel-control-prev"
                href="#recommendationIndicators"
                role="button"
                data-slide="prev"
              >
                <span
                  className="carousel-control-prev-icon"
                  aria-hidden="true"
                />
                <span className="sr-only">Previous</span>
              </a>
              <a
                className="carousel-control-next"
                href="#recommendationIndicators"
                role="button"
                data-slide="next"
              >
                <span
                  className="carousel-control-next-icon"
                  aria-hidden="true"
                />
                <span className="sr-only">Next</span>
              </a>
            </div>
          )}
        <div
          className="container container-fluid"
          style={{
            display: 'flex',
            flexWrap: 'wrap',
            margin: '0 auto',
            justifyContent: 'space-between'
          }}
        >
          {allMovies &&
            allMovies.map(movie => (
              <div
                key={movie.id}
                className="card"
                style={{ width: '12rem', margin: '10px', border: 'none' }}
                onClick={() => this.handleOnClick(movie)}
              >
                <img src={movie.imageUrl} alt="movie thumbnail" height="250" />
              </div>
            ))}
        </div>
      </div>
    );
  }
}

export default compose(
  graphql(GET_ALL_MOVIES, { name: 'getAllMovies' }),
  graphql(GET_ALL_RECOMMENDED_MOVIES, { name: 'getAllMoviesRecommendation' }),
  graphql(ADD_WATCHED_MOVIE_MUTATION, { name: 'addWatchedMovie' })
)(withRouter(Landing));
