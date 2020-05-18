import PropTypes from "prop-types";
import React, { Component } from "react";
import classNames from "classnames";
import { graphql, compose } from "react-apollo";

import { LOG_INTERACTION } from "../utils/graphql_tags";
const propTypes = {
  actions: PropTypes.object,
  player: PropTypes.object,
  position: PropTypes.string,
  className: PropTypes.string
};

const defaultProps = {
  position: "left"
};

class CustomBigPlayButton extends Component {
  constructor(props, context) {
    super(props, context);

    this.handleClick = this.handleClick.bind(this);
  }

  async handleClick() {
    const { actions, player } = this.props;
    if (player.paused) {
      actions.play();
      await this.props.logInteraction({
        variables: {
          time: new Date(),
          type: "play"
        }
      });
    } else {
      actions.pause();
      await this.props.logInteraction({
        variables: {
          time: new Date(),
          type: "pause"
        }
      });
    }
  }

  render() {
    const { player, position } = this.props;
    return (
      <button
        className={classNames(
          "video-react-big-play-button",
          `video-react-big-play-button-${position}`,
          this.props.className,
          {
            "big-play-button-hide": player.hasStarted || !player.currentSrc
          }
        )}
        type="button"
        aria-live="polite"
        tabIndex="0"
        onClick={this.handleClick}
      >
        <span className="video-react-control-text">Play Video</span>
      </button>
    );
  }
}

CustomBigPlayButton.propTypes = propTypes;
CustomBigPlayButton.defaultProps = defaultProps;

export default compose(graphql(LOG_INTERACTION, { name: "logInteraction" }))(
  CustomBigPlayButton
);
