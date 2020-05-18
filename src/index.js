import React from 'react';
import ReactDOM from 'react-dom';
import { ApolloProvider } from 'react-apollo';
import { ApolloClient } from 'apollo-client';
import { HttpLink } from 'apollo-link-http';
import { InMemoryCache } from 'apollo-cache-inmemory';
import { BrowserRouter } from 'react-router-dom';
import { AUTH_TOKEN } from './utils/constants';
import { ApolloLink } from 'apollo-client-preset';

import registerServiceWorker from './registerServiceWorker';
import App from './App';

const httpLink = new HttpLink({ uri: `http://localhost:8080/graphqlApi` });

const middlewareAuthLink = new ApolloLink((operation, forward) => {
  const token = localStorage.getItem(AUTH_TOKEN);
  const authorizationHeader = token ? `Bearer ${token}` : null;
  if (authorizationHeader) {
    operation.setContext({
      headers: {
        Authorization: authorizationHeader
      }
    });
  }
  return forward(operation);
});

const httpLinkWithAuthToken = middlewareAuthLink.concat(httpLink);

const client = new ApolloClient({
  // By default, this client will send queries to the
  //  `/graphql` endpoint on the same host
  link: httpLinkWithAuthToken,
  cache: new InMemoryCache()
});

ReactDOM.render(
  <BrowserRouter>
    <ApolloProvider client={client}>
      <App />
    </ApolloProvider>
  </BrowserRouter>,
  document.getElementById('root')
);
registerServiceWorker();
