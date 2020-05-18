package com.recflix.utils;

import com.recflix.app.AuthContext;
import java.io.UnsupportedEncodingException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

/**
 * Constants
 */
public abstract class AuthUtils {

    private static String APP_SECRET = "recflix";

    public static String getAppSecret() {
        return APP_SECRET;
    }

    public static String getUserId(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(APP_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim("userId").asString();
        } catch (UnsupportedEncodingException exception) {
            //UTF-8 encoding not supported
        } catch (JWTVerificationException exception) {
            //Invalid signature/claims
        }
        throw new GraphQLException("Not Authenticated");
    }
}
