/**
 * Manage actions to authenticate a user
 *
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gabriel Roch, Christian Zaccaria
 */

package com.amt.app.controllers;

import com.amt.app.auth.Provider;
import com.amt.app.auth.User;
import com.amt.app.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AuthController {

    private UserService userService;
    private UserService service;

    public AuthController(UserService userService, UserService service) {
        this.userService = userService;
        this.service = service;
    }

    /**
     * Display login formular
     * @return page to display
     */
    @GetMapping("/auth/login")
    public String login(Model model) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        model.addAttribute("login", provider.login(""));
        return "login_form";
    }

    /**
     * Display login formular
     * @return page to display
     */
    @GetMapping("/auth")
    public String login2(Model model) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        model.addAttribute("login", provider.login(""));
        return "login_form";
    }

    static class UserLogin {
        @Getter
        @Setter
        String username;
        @Getter
        @Setter
        String password;
    }

    /**
     * Display login formular
     * @return page to display
     */
    @PostMapping("/auth/perform")
    public String login_perform(@ModelAttribute UserLogin t, HttpServletResponse response, Model model) throws IOException {
        Provider provider;
        User login;
        try {
            provider = new Provider(service, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
            model.addAttribute("login", provider.login(""));
            login = provider.login(t.getUsername(), t.getPassword());
        } catch (Exception e) {
            model.addAttribute("error_message", "Echec de l'authentification");
            return "login_form";
        }
        Cookie jwt = new Cookie("jwt", login.getJwt());
        jwt.setPath("/");
        jwt.setSecure(true);
        jwt.setHttpOnly(true);
        response.addCookie(jwt);
        response.sendRedirect("/");
        model.addAttribute("login", login);
        return "index";
    }

    /**
     * Display login formular
     * @return page to display
     */    @GetMapping("/auth/logout")
    @ResponseBody
    public String logout_perform(HttpServletResponse response, Model model) throws IOException {
        Provider provider;
        Cookie jwt = new Cookie("jwt", "");
        jwt.setPath("/");
        jwt.setSecure(true);
        jwt.setHttpOnly(true);
        jwt.setMaxAge(0);
        response.addCookie(jwt);
        response.sendRedirect("/");
        return "logout ok";
    }

    /**
     * Display login formular
     * @return page to display
     */    @GetMapping("/auth/role")
    @ResponseBody
    public String check_role(@CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(service, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        return login.getRole();
    }

    /**
     * Display register formular
     * @return page to display
     */    @GetMapping("/auth/signup")
    public String sign_up(Model model) {
        return "signup_form";
    }


    static class UserRegister {
        @Getter
        @Setter
        String username;
        @Getter
        @Setter
        String password;
        @Getter
        @Setter
        String password_confirm;
    }

    /**
     * Process to register a user
     * @return page to display
     */    @PostMapping("/auth/signup_perform")
    public String signup_perform(@ModelAttribute UserRegister t, HttpServletResponse response, Model model) throws Exception {
        Provider provider = new Provider(service, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        if (t.getPassword().equals(t.getPassword_confirm())) {
            // process
            try {
                provider.register(t.getUsername(), t.getPassword());
                response.sendRedirect("/auth/login");
                model.addAttribute("login", provider.login(""));
                return "login_form";
            } catch (Exception e) {
                model.addAttribute("error_message", "Echec de l'enregistrement du compte");
                return "signup_form";
            }
        } else {
            model.addAttribute("error_message", "Le mot de passe et sa confirmation ne corresponds pas");
            return "signup_form";
        }
    }
}
