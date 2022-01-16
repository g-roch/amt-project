/**
 *
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gaby Roch, Christian Zaccaria
 */
package com.amt.auth.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import javafx.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    static class UserRequest {
        public String username = "";
        public String password = "";
    }

    interface Response {

    }

    static class Account implements Response {
        public long id;
        public String username;
        public String role;

        public Account() {
            id = -1;
            username = null;
            role = null;
        }

        public Account(long id, String username, String role) {
            this.id = id;
            this.username = username;
            this.role = role;
        }
    }

    static class SuccessLogin implements Response {
        public String token;
        public Account account;
    }

    static class MinimalError implements Response {
        public String error;
    }

    static class ErrorDetail {
        public String property;
        public String message;

        public ErrorDetail() {
            property = null;
            message = null;
        }

        public ErrorDetail(String property, String message) {
            this.property = property;
            this.message = message;
        }
    }

    static class Error implements Response {
        public ArrayList<ErrorDetail> errors = new ArrayList<>();
    }

    private final HashMap<Pair<String, String>, Account> database;
    private long next_id = 11;
    private final String jwt_secret = "czvFbg2kmvqbcu(7Ux+c";
    private final String jwt_issuer = "IICT";

    public AuthController() {
        database = new HashMap<>();
        database.put(new Pair<>("gaby", "gaby"), new Account(1, "gaby", "user"));
        database.put(new Pair<>("gaby2", "gaby"), new Account(2, "gaby", "admin"));
        database.put(new Pair<>("pianorgue", ".heig-"), new Account(10, "pianorgue", "admin"));
    }

    @PostMapping(value = "/auth/login", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response login(
            @RequestBody UserRequest user
    ) {
        if (database.containsKey(new Pair<>(user.username, user.password))) {
            SuccessLogin successLogin = new SuccessLogin();
            Algorithm algorithm = Algorithm.HMAC256(jwt_secret);
            successLogin.account = database.get(new Pair<>(user.username, user.password));
            successLogin.token = JWT.create()
                    .withIssuer(jwt_issuer)
                    .withSubject(successLogin.account.username)
                    .withIssuedAt(new Date())
                    .withExpiresAt(Date.from(
                            LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.UTC)
                    ))
                    .withClaim("role", successLogin.account.role)
                    .sign(algorithm)
            ;
            return successLogin;
        }

        MinimalError minimalError = new MinimalError();
        minimalError.error = "Credentials are incorrect";
        return minimalError;
    }

    @PostMapping(value = "/accounts/register", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response register(
            @RequestBody UserRequest user
    ) {
        Error error = new Error();
        if (user.username == null || user.password == null) {
            if (user.username == null) {
                error.errors.add(new ErrorDetail("username", "Username is required"));
            }
            if (user.password == null) {
                error.errors.add(new ErrorDetail("password", "Username is required"));
            }
            return error;
        }
        if (user.username.length() < 3) {
            error.errors.add(new ErrorDetail("username", "Minimal size is 3"));
            return error;
        }
        if (user.username.length() > 32) {
            error.errors.add(new ErrorDetail("username", "Maximal size is 32"));
            return error;
        }
        if (user.password.length() < 8) {
            error.errors.add(new ErrorDetail("password", "Minimal size is 8"));
            return error;
        }
        if (user.password.length() > 32) {
            error.errors.add(new ErrorDetail("password", "Maximal size is 32"));
            return error;
        }
        for (Map.Entry<Pair<String, String>, Account> i : database.entrySet()) {
            if (i.getKey().getKey().equals(user.username)) {
                error.errors.add(new ErrorDetail("username", "Username already exists"));
                return error;
            }
        }
        Account account = new Account(next_id++, user.username, "user");
        database.put(new Pair<>(user.username, user.password), account);
        return account;
    }
}

