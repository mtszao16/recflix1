import PropTypes from 'prop-types';
import React, { Component } from 'react';
import classNames from 'classnames';
import { withRouter } from 'react-router';
import { graphql, compose } from 'react-apollo';
import { LOG_INTERACTION } from '../utils/graphql_tags';

const propTypes = {
  actions: PropTypes.object,
  className: PropTypes.string,
  seconds: PropTypes.oneOf([5, 10, 30])
};

const defaultProps = {
  seconds: 10
};

export default mode => {
  class CustomForwardReplayControl extends Component {
    constructor(props, context) {
      super(props, context);
      this.handleClick = this.handleClick.bind(this);
    }

    async handleClick() {
      const { actions, seconds, match } = this.props;
      // Depends mode to implement different actions
      if (mode === 'forward') {
        actions.forward(seconds);
        await this.props.logInteraction({
          variables: {
            type: 'forward',
            movieId: match.params.movieId
          }
        });
      } else {
        actions.replay(seconds);
        await this.props.logInteraction({
          variables: {
            type: 'backward',
            movieId: match.params.movieId
          }
        });
      }
    }

    render() {
      const { seconds, className } = this.props;
      return (
        <button
          ref={c => {
            this.button = c;
          }}
          className={classNames(
            className,
            {
              [`video-react-icon-${mode}-${seconds}`]: true,
              [`video-react-${mode}-control`]: true
            },
            'video-react-control video-react-button video-react-icon'
          )}
          onClick={this.handleClick}
        >
          <span className="video-react-control-text">{`${mode} ${seconds} seconds`}</span>
        </button>
      );
    }
  }

  CustomForwardReplayControl.propTypes = propTypes;
  CustomForwardReplayControl.defaultProps = defaultProps;
  return compose(graphql(LOG_INTERACTION, { name: 'logInteraction' }))(
    withRouter(CustomForwardReplayControl)
  );
};
