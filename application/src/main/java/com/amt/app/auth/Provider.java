/**
 * Provide a objet can connect to an authentication serveur and can check pair username/password
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gaby Roch, Christian Zaccaria
 */
package com.amt.app.auth;

import com.amt.app.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.gson.Gson;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;


public class Provider {

    /**
     * Service for access to user stored in DB
     */
    private final UserService service;
    /**
     * Algorithm used for sign JWT
     */
    private final Algorithm algorithm;
    /**
     * JWT issuer (verified when JWT is verified)
     */
    private final String issuer;
    /**
     * Internal object used for verify JWT
     */
    private final JWTVerifier verifier;
    /**
     * URL of authentication server
     */
    private final String server;

    public Provider(UserService service,String algorithm, String secret, String issuer, String server) throws Exception {
        this(service, algorithm, secret.getBytes(), issuer, server);
    }
    public Provider(UserService service,String algorithm, byte[] secret, String issuer, String server) throws Exception {
        if (!algorithm.equals("HS256")) {
            throw new Exception("Only HS256 algorithm is supported");
        }
        this.algorithm = Algorithm.HMAC256(secret);
        this.service = service;
        this.issuer = issuer;
        this.verifier = JWT.require(this.algorithm)
                .withIssuer(this.issuer)
                .build();
        this.server = server;
    }

    /**
     * Get a auth.User object correspond of token if token is a valid JWT
     * @param token The JWT token
     * @return The auth.User after authentication check
     * @throws JWTVerificationException If verification failed
     */
    public User login(String token) throws JWTVerificationException {
        if (token.length() == 0) {
            return User.guest();
        } else {
            DecodedJWT jwt = verifier.verify(token);
            return User.fromJson(token, new String(Base64.getDecoder().decode(jwt.getPayload())), service);
        }
    }

    /**
     * Check online pair username/password and return a auth.User object if credentials are valid.
     * @param username The username
     * @param password The password
     * @return auth.User if credentials are valid and if JWT returned by auth server is valid
     * @throws Exception If authentication failed
     */
    public User login(String username, String password) throws Exception {

        Content response = Request.Post(server + "/auth/login")
                .bodyString(payload(username, password), ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();

        ServerLoginResponse reponseObject = ServerLoginResponse.fromJson(response.toString());

        System.out.println(reponseObject.getToken());
        System.out.println(reponseObject.getAccount().getId());
        System.out.println(reponseObject.getAccount().getUsername());
        System.out.println(reponseObject.getAccount().getRole());

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .withSubject(reponseObject.getAccount().username)
                .build();
        verifier.verify(reponseObject.getToken());

        User user = login(reponseObject.getToken());

        if (!user.getRole().equals(reponseObject.getAccount().getRole())) {
            throw new Exception("Authentication server error");
        }

        return user;
    }

    /**
     * Transform pair username/password to a JSON.
     * @return the JSON
     */
    private String payload(String username, String password) {
        HashMap<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        return new Gson().toJson(data);
    }

    /**
     * Register a user. If the auth server return an error, an exception is throwed
     * @param username the username
     * @param password the password
     * @throws IOException if register failed
     */
    public void register(String username, String password) throws IOException {
        Content response = Request.Post(server + "/accounts/register")
                .bodyString(payload(username, password), ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();
    }
}
