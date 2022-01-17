/**
 * a class used for represent a valid user or a guest
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gaby Roch, Christian Zaccaria
 */
package com.amt.app.auth;

import com.amt.app.service.UserService;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

public class User {

    /**
     * Internal ID of user, used on some table
     */
    @Getter
    private Integer id;

    /**
     * Username of user
     */
    @Getter
    @SerializedName("sub")
    private String username;

    /**
     * role of user. If user isn't connected, this is 'guest'
     */
    @Getter
    private String role;

    /**
     * issuer emit the JWT
     */
    @Getter
    @SerializedName("iss")
    private String issuer;

    /**
     * the JWT, for set the cookie
     */
    @Getter
    private String jwt;

    /**
     * decode a jwt and return an object
     * @param jwt a VALID jwt
     * @param payload just the content of jwt
     * @param service the service for get user from DB
     * @return the User object
     */
    public static User fromJson(String jwt, String payload, UserService service) {
        Gson gson = new Gson();
        User user = gson.fromJson(payload, User.class);
        user.jwt = jwt;
        com.amt.app.entities.User dbUser = service.getByUsername(user.username);
        if(dbUser == null) {
            dbUser = new com.amt.app.entities.User();
            dbUser.setUsername(user.username);
            dbUser = service.addUser(dbUser);
        }
        user.id = dbUser.getId();
        System.out.println(user.id);
        System.out.println(user.id);
        return user;
    }

    /**
     * return a guest User (role = 'guest')
     * @return a guest User
     */
    public static User guest() {
        User user = new User();
        user.role = "guest";
        return user;
    }
}
