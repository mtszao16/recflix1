import React, { Component } from 'react';
import { withRouter } from 'react-router';

class NoMatch extends Component {
  render() {
    return (
      <div className="container">
        <h1 className="title">404</h1>
      </div>
    );
  }
}

export default withRouter(NoMatch);
