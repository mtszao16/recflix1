import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { withRouter } from 'react-router';
import classNames from 'classnames';
import { graphql, compose } from 'react-apollo';
import { LOG_INTERACTION } from '../utils/graphql_tags';

import {
  Slider,
  PlayProgressBar,
  LoadProgressBar,
  MouseTimeDisplay
} from 'video-react';
import { formatTime } from '../utils';

const propTypes = {
  player: PropTypes.object,
  mouseTime: PropTypes.object,
  actions: PropTypes.object,
  className: PropTypes.string
};

class CustomSeekBar extends Component {
  constructor(props, context) {
    super(props, context);

    this.getPercent = this.getPercent.bind(this);
    this.getNewTime = this.getNewTime.bind(this);
    this.stepForward = this.stepForward.bind(this);
    this.stepBack = this.stepBack.bind(this);

    this.handleMouseDown = this.handleMouseDown.bind(this);
    this.handleMouseMove = this.handleMouseMove.bind(this);
    this.handleMouseUp = this.handleMouseUp.bind(this);
  }

  componentDidMount() {}

  componentDidUpdate() {}

  /**
   * Get percentage of video played
   *
   * @return {Number} Percentage played
   * @method getPercent
   */
  getPercent() {
    const { currentTime, seekingTime, duration } = this.props.player;
    const time = seekingTime || currentTime;
    const percent = time / duration;
    return percent >= 1 ? 1 : percent;
  }

  getNewTime(event) {
    const {
      player: { duration, currentTime }
    } = this.props;
    const distance = this.slider.calculateDistance(event);
    const newTime = distance * duration;

    const direction = newTime > currentTime ? 'forward' : 'backward';
    this.setState({ direction });

    // Don't let video end while scrubbing.
    return newTime === duration ? newTime - 0.1 : newTime;
  }

  handleMouseDown() {}

  async handleMouseUp(event) {
    const {
      actions,
      player: { currentTime },
      match
    } = this.props;
    const newTime = this.getNewTime(event);
    // Set new time (tell video to seek to new time)
    actions.seek(newTime);
    actions.handleEndSeeking(newTime);

    const duration =
      this.state.direction === 'forward'
        ? newTime - currentTime
        : currentTime - newTime;

    await this.props.logInteraction({
      variables: {
        type: `${this.state.direction} seek`,
        movieId: match.params.movieId,
        amount: duration
      }
    });
  }

  handleMouseMove(event) {
    const { actions } = this.props;
    const newTime = this.getNewTime(event);
    actions.handleSeekingTime(newTime);
  }

  async stepForward() {
    const { actions, match } = this.props;
    actions.forward(5);
    await this.props.logInteraction({
      variables: {
        type: 'forward',
        movieId: match.params.movieId
      }
    });
  }

  async stepBack() {
    const { actions, match } = this.props;
    actions.replay(5);
    await this.props.logInteraction({
      variables: {
        type: 'backward',
        movieId: match.params.movieId
      }
    });
  }

  render() {
    const {
      player: { currentTime, seekingTime, duration, buffered },
      mouseTime
    } = this.props;
    const time = seekingTime || currentTime;

    return (
      <Slider
        ref={input => {
          this.slider = input;
        }}
        label="video progress bar"
        className={classNames(
          'video-react-progress-holder',
          this.props.className
        )}
        valuenow={(this.getPercent() * 100).toFixed(2)}
        valuetext={formatTime(time, duration)}
        onMouseDown={this.handleMouseDown}
        onMouseMove={this.handleMouseMove}
        onMouseUp={this.handleMouseUp}
        getPercent={this.getPercent}
        stepForward={this.stepForward}
        stepBack={this.stepBack}
      >
        <LoadProgressBar
          buffered={buffered}
          currentTime={time}
          duration={duration}
        />
        <MouseTimeDisplay duration={duration} mouseTime={mouseTime} />
        <PlayProgressBar currentTime={time} duration={duration} />
      </Slider>
    );
  }
}

CustomSeekBar.propTypes = propTypes;

export default compose(graphql(LOG_INTERACTION, { name: 'logInteraction' }))(
  withRouter(CustomSeekBar)
);
