package com.amt.app.auth;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;

public class User {

    @Getter
    private Integer id;

    @Getter
    @SerializedName("sub")
    private String username;

    @Getter
    private String role;

    @Getter
    @SerializedName("iss")
    private String issuer;

    public static User fromJson(String payload) {
        Gson gson = new Gson();
        return gson.fromJson(payload, User.class);
    }
}
