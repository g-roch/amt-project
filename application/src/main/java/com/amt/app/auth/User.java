package com.amt.app.auth;

import com.amt.app.service.UserService;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

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

    @Getter
    private String jwt;

    public static User fromJson(String jwt, String payload, UserService service) {
        Gson gson = new Gson();
        User user = gson.fromJson(payload, User.class);
        user.jwt = jwt;
        user.id = service.getByUsername(user.username).getId();
        return user;
    }
    public static User guest() {
        User user = new User();
        user.role = "guest";
        return user;
    }
}
