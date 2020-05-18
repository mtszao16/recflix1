import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { withRouter } from 'react-router';
import { AUTH_TOKEN } from '../utils/constants';

class Header extends Component {
  render() {
    const authToken = localStorage.getItem(AUTH_TOKEN);
    return (
      <nav className="navbar" style={{ background: 'black', height: '60px' }}>
        <div className="navbar-header">
          <Link className="navbar-brand" to="/">
            <img
              alt="Logo"
              src="https://raw.githubusercontent.com/UtkarshGupta-CS/recflix/master/public/assets/images/recflixlogo.png"
              id="logo"
            />
          </Link>
        </div>
        <div className="flex flex-fixed">
          {authToken ? (
            <div className="navbar">
              <Link
                to="/login"
                className="btn btn-primary"
                onClick={() => {
                  localStorage.removeItem(AUTH_TOKEN);
                }}
              >
                Log Out
              </Link>
            </div>
          ) : this.props.location.pathname !== '/login' ? (
            <div className="navbar">
              <Link to="/login" className="btn btn-primary logout-btn">
                Log In
              </Link>
            </div>
          ) : (
            <span />
          )}
        </div>
      </nav>
    );
  }
}

export default withRouter(Header);
