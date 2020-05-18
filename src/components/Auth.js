import React, { Component } from 'react';
import { graphql, compose } from 'react-apollo';
import { AUTH_TOKEN } from '../utils/constants';
import { SIGNIN_MUTATION, SIGNUP_MUTATION } from '../utils/graphql_tags';
import { validateNonEmpty } from '../utils/validationUtils';

import { FormErrors } from './FormError';
class Auth extends Component {
  state = {
    login: true, // switch between Login and SignUp
    email: '',
    password: '',
    name: '',
    formErrors: { email: '', password: '', name: '' },
    emailValid: false,
    passwordValid: false,
    nameValid: false,
    formValid: false
  };

  handleUserInput = e => {
    const name = e.target.name;
    const value = e.target.value;
    this.setState({ [name]: value }, () => {
      this.validateField(name, value);
    });
  };

  validateField(fieldName, value) {
    let { emailValid, nameValid, passwordValid, formErrors } = this.state;

    switch (fieldName) {
      case 'name':
        nameValid = validateNonEmpty(value);
        formErrors.name = nameValid ? '' : ' is invalid';
        break;

      case 'email':
        emailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
        formErrors.email = emailValid ? '' : ' is invalid';
        break;
      case 'password':
        passwordValid = value.length >= 6;
        formErrors.password = passwordValid ? '' : ' is too short';
        break;
      default:
        break;
    }
    this.setState(
      {
        formErrors: formErrors,
        nameValid: nameValid,
        emailValid: emailValid,
        passwordValid: passwordValid
      },
      this.validateForm
    );
  }

  validateForm() {
    if (this.state.login) {
      this.setState({
        formValid: this.state.emailValid && this.state.passwordValid
      });
    } else {
      this.setState({
        formValid:
          this.state.emailValid &&
          this.state.passwordValid &&
          this.state.nameValid
      });
    }
  }

  errorClass(error) {
    return error.length === 0 ? '' : 'has-error';
  }

  handleOnClick = event => {
    event.preventDefault();
    if (this.state.formValid) this._confirm();
  };

  render() {
    return (
      <div className="auth-bg">
        <div className="row">
          <div className="col">
            <div className="form">
              <ul className="nav nav-pills nav-fill" role="tablist">
                <li className="nav-item">
                  <a
                    className="nav-link active"
                    href="#login"
                    role="tab"
                    data-toggle="tab"
                    onClick={() => {
                      this.setState(prevState => {
                        if (!prevState.login) {
                          return {
                            login: true,
                            email: '',
                            password: '',
                            name: '',
                            formErrors: { email: '', password: '', name: '' },
                            emailValid: false,
                            passwordValid: false,
                            nameValid: false,
                            formValid: false
                          };
                        }
                      });
                    }}
                  >
                    Log In
                  </a>
                </li>
                <li className="nav-item">
                  <a
                    className="nav-link"
                    href="#signup"
                    role="tab"
                    data-toggle="tab"
                    onClick={() => {
                      this.setState(prevState => {
                        if (prevState.login) {
                          return {
                            login: false,
                            email: '',
                            password: '',
                            name: '',
                            formErrors: { email: '', password: '', name: '' },
                            emailValid: false,
                            passwordValid: false,
                            nameValid: false,
                            formValid: false
                          };
                        }
                      });
                    }}
                  >
                    Sign Up
                  </a>
                </li>
              </ul>
              <div className="panel panel-default">
                <FormErrors formErrors={this.state.formErrors} />
              </div>
              <div className="tab-content">
                <div role="tabpanel" className="tab-pane active" id="login">
                  <form className="needs-validation">
                    <div className="form-row">
                      <div
                        className={`col ${this.errorClass(
                          this.state.formErrors.email
                        )}`}
                      >
                        <label htmlFor="email">Email Address</label>
                        <input
                          name="email"
                          value={this.state.email}
                          onChange={e => this.handleUserInput(e)}
                          type="email"
                          className="form-control"
                          placeholder="Email Address"
                          required
                        />
                      </div>
                    </div>
                    <div className="form-row">
                      <div
                        className={`col ${this.errorClass(
                          this.state.formErrors.password
                        )}`}
                      >
                        <label htmlFor="password">Password</label>
                        <input
                          name="password"
                          value={this.state.password}
                          onChange={e => this.handleUserInput(e)}
                          type="password"
                          className="form-control"
                          placeholder="Password"
                          required
                        />
                      </div>
                    </div>
                    <button
                      className="btn btn-primary btn-block"
                      style={{ marginTop: '10px' }}
                      type="submit"
                      disabled={!this.state.formValid}
                      onClick={e => this.handleOnClick(e)}
                    >
                      Log In
                    </button>
                  </form>
                </div>

                <div role="tabpanel" className="tab-pane" id="signup">
                  <form className="needs-validation">
                    <div className="form-row">
                      <div
                        className={`col ${this.errorClass(
                          this.state.formErrors.name
                        )}`}
                      >
                        <label htmlFor="name">Name</label>
                        <input
                          value={this.state.name}
                          onChange={e => this.handleUserInput(e)}
                          type="text"
                          className="form-control"
                          placeholder="Name"
                          name="name"
                        />
                      </div>
                    </div>
                    <div className="form-row">
                      <div
                        className={`col ${this.errorClass(
                          this.state.formErrors.email
                        )}`}
                      >
                        <label htmlFor="email">Email Address</label>
                        <input
                          value={this.state.email}
                          onChange={e => this.handleUserInput(e)}
                          type="email"
                          className="form-control"
                          placeholder="Email Address"
                          name="email"
                        />
                      </div>
                    </div>
                    <div className="form-row">
                      <div
                        className={`col ${this.errorClass(
                          this.state.formErrors.password
                        )}`}
                      >
                        <label htmlFor="password">Password</label>
                        <input
                          value={this.state.password}
                          onChange={e => this.handleUserInput(e)}
                          type="password"
                          className="form-control"
                          placeholder="Password"
                          name="password"
                        />
                        <div className="invalid-feedback">
                          Please choose a password.
                        </div>
                      </div>
                    </div>
                    <button
                      className="btn btn-primary btn-block"
                      style={{ marginTop: '10px' }}
                      type="submit"
                      onClick={e => this.handleOnClick(e)}
                      disabled={!this.state.formValid}
                    >
                      Sign Up
                    </button>
                  </form>
                </div>
              </div>
            </div>
          </div>
          <div className="col-md">
            <h1> Almost there!</h1>
            <p>
              Sign up and join others in the movie recommendation network and
              receive movie recommendations that are right for you.
            </p>
          </div>
        </div>
      </div>
    );
  }

  _confirm = async () => {
    const { name, email, password } = this.state;
    if (this.state.login) {
      const result = await this.props.loginMutation({
        variables: {
          auth: {
            email,
            password
          }
        }
      });
      const { token } = result.data.signinUser;
      this._saveUserData(token);
    } else {
      const result = await this.props.signupMutation({
        variables: {
          name,
          authProvider: {
            email,
            password
          }
        }
      });
    }
    this.props.history.push(`/`);
  };

  _saveUserData = token => {
    localStorage.setItem(AUTH_TOKEN, token);
  };
}

export default compose(
  graphql(SIGNUP_MUTATION, { name: 'signupMutation' }),
  graphql(SIGNIN_MUTATION, { name: 'loginMutation' })
)(Auth);
