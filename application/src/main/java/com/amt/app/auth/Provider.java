package com.amt.app.auth;

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

    private final Algorithm algorithm;
    private final String issuer;
    private final JWTVerifier verifier;
    private final String server;

    public Provider(String algorithm, String secret, String issuer, String server) throws Exception {
        this(algorithm, secret.getBytes(), issuer, server);
    }
    public Provider(String algorithm, byte[] secret, String issuer, String server) throws Exception {
        if (!algorithm.equals("HS256")) {
            throw new Exception("Only HS256 algorithm is supported");
        }
        this.algorithm = Algorithm.HMAC256(secret);
        this.issuer = issuer;
        this.verifier = JWT.require(this.algorithm)
                .withIssuer(this.issuer)
                .build();
        this.server = server;
    }

    public User login(String token) throws JWTVerificationException {
        if (token.length() == 0) {
            return User.guest();
        } else {
            DecodedJWT jwt = verifier.verify(token);
            return User.fromJson(token, new String(Base64.getDecoder().decode(jwt.getPayload())));
        }
    }
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
                .build(); //Reusable verifier instance
        verifier.verify(reponseObject.getToken());

        User user = login(reponseObject.getToken());

        if (!user.getRole().equals(reponseObject.getAccount().getRole())) {
            throw new Exception("Authentication server error");
        }


        return user;

    }

    private String payload(String username, String password) {
        HashMap<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        String payload = new Gson().toJson(data);
        return payload;
    }

    public void register(String username, String password) throws IOException {
        Content response = Request.Post(server + "/accounts/register")
                .bodyString(payload(username, password), ContentType.APPLICATION_JSON)
                .execute()
                .returnContent();
    }

}
