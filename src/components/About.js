import React, { Component } from 'react';
import { withRouter } from 'react-router';

class About extends Component {
  render() {
    return (
      <div className="text-center">
        <h1>Team</h1>
        <div className="row">
          <div className="col col-md-4">
            <h4>Utkarsh Kumar Gupta</h4>
            <p>Hacker</p>
          </div>
          <div className="col col-md-4">
            <h4>Sumit Kumar</h4>
            <p>Data Scientist</p>
          </div>
          <div className="col col-md-4">
            <h4>Anand Preet Samuel</h4>
            <p>Designer</p>
          </div>
        </div>
        <div className="row">
          <div className="col col-md-12 text-center">
            <h4>Himanshu Sahu</h4>
            <p>Mentor</p>
          </div>
        </div>
      </div>
    );
  }
}

export default withRouter(About);
