package com.amt.app.auth;


import com.google.gson.Gson;
import lombok.Getter;

public class User {

    @Getter
    private int id;

    @Getter
    private String username;

    @Getter
    private String role;

    public static User fromJson(String payload) {
        Gson gson = new Gson();
        return gson.fromJson(payload, User.class);
    }
}
