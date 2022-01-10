package com.amt.app;

import com.amt.app.auth.Provider;
import com.amt.app.auth.User;
import com.amt.app.service.UserService;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AuthTest {

    /*
    @Autowired
    private UserService userService;

    @Test
    public void expiredToken() throws Exception {
        Provider provider = new Provider(userService,"HS256", "secret", "IICT", "");
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaWFub3JvZ3VlIiwicm9sZSI6ImFkbWluIiwiaXNzIjoiSUlDVCIsImV4cCI6MTYzODA5NDYzMCwiaWF0IjoxNjM4MDk0NjMwfQ.E92NyuYke7UzxVzGKq-8735dxgYAPSimik0RcIGX7oc";
        Exception ex = assertThrows(TokenExpiredException.class, () -> {
            User user = provider.login(jwt);
        });
    }

    @Test
    public void futureToken() throws Exception {
        Provider provider = new Provider(userService, "HS256", "secret", "IICT", "");
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaWFub3JvZ3VlIiwicm9sZSI6ImFkbWluIiwiaXNzIjoiSUlDVCIsImV4cCI6NTYzODA5NDYzMCwiaWF0Ijo1NjM4MDk0NjMwfQ.7_VWlrNLHxNWi3kPIOTVafYRki1oaUQSxf2y5TTbekM";
        Exception ex = assertThrows(InvalidClaimException.class, () -> {
            User user = provider.login(jwt);
        });
    }

    @Test
    public void goodToken() throws Exception {
        Provider provider = new Provider(userService,"HS256", "secret", "IICT", "");
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaWFub3JvZ3VlIiwicm9sZSI6ImFkbWluIiwiaXNzIjoiSUlDVCIsImV4cCI6NTYzODA5NDYzMCwiaWF0IjoxMjM0NTY3ODkwfQ.uf22EV4TF352qCUwtMVFfK9Qt4CKEmMgdgd0GTK5s1o";
        User user = provider.login(jwt);
        assertEquals("pianorogue", user.getUsername());
        assertEquals("admin", user.getRole());
        assertEquals("IICT", user.getIssuer());
        assertEquals(1, user.getId());
    }
     */
}
