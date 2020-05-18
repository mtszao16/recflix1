import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { withRouter } from 'react-router';
import classNames from 'classnames';
import { graphql, compose } from 'react-apollo';

import { LOG_INTERACTION } from '../utils/graphql_tags';
const propTypes = {
  actions: PropTypes.object,
  player: PropTypes.object,
  className: PropTypes.string
};

class CustomPlayToggle extends Component {
  constructor(props, context) {
    super(props, context);
    this.handleClick = this.handleClick.bind(this);
  }

  async handleClick() {
    const { actions, player, match } = this.props;
    if (player.paused) {
      actions.play();
      await this.props.logInteraction({
        variables: {
          type: 'play',
          movieId: match.params.movieId
        }
      });
    } else {
      actions.pause();
      await this.props.logInteraction({
        variables: {
          type: 'pause',
          movieId: match.params.movieId
        }
      });
    }
  }

  render() {
    const { player, className } = this.props;
    const controlText = player.paused ? 'Play' : 'Pause';

    return (
      <button
        ref={c => {
          this.button = c;
        }}
        className={classNames(className, {
          'video-react-play-control': true,
          'video-react-control': true,
          'video-react-button': true,
          'video-react-paused': player.paused,
          'video-react-playing': !player.paused
        })}
        tabIndex="0"
        onClick={this.handleClick}
      >
        <span className="video-react-control-text">{controlText}</span>
      </button>
    );
  }
}

CustomPlayToggle.propTypes = propTypes;

export default compose(graphql(LOG_INTERACTION, { name: 'logInteraction' }))(
  withRouter(CustomPlayToggle)
);
