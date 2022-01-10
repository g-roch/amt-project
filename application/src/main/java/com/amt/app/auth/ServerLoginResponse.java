package com.amt.app.auth;

import com.google.gson.Gson;
import lombok.Getter;

public class ServerLoginResponse {

    static class Account {

        @Getter
        long id;

        @Getter
        String username;

        @Getter
        String role;
    }

    @Getter
    private String token;

    @Getter
    private Account account;

    public static ServerLoginResponse fromJson(String payload) {
        Gson gson = new Gson();
        return gson.fromJson(payload, ServerLoginResponse.class);
    }
}
