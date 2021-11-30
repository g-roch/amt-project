package com.amt.app.controllers;

import com.amt.app.auth.Provider;
import com.amt.app.auth.User;
import com.amt.app.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Controller
public class AuthController {

//    @Autowired
//    private Provider authProvider;

//    public void setArticleService(ArticleService articleService){
//        this.service = articleService;
//    }

    @Autowired
    private UserService service;

    // Affichage formulaire de login
    @GetMapping("/auth/login")
    public String login(Model model) {
        return "login_form";
    }

    static class T {
        @Getter @Setter
        String username;
        @Getter @Setter
        String password;
    }
    
    // Affichage formulaire de login
    @PostMapping("/auth/perform")
    @ResponseBody
    public String login_perform(@ModelAttribute T t, HttpServletResponse response, Model model) throws IOException {
        Provider provider;
        User login;
        try {
            provider = new Provider(service, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
            login = provider.login(t.getUsername(), t.getPassword());
        } catch (Exception e) {
            response.sendRedirect("/auth?error=");
            return "Login error";
        }
        Cookie jwt = new Cookie("jwt", login.getJwt());
        jwt.setPath("/");
        jwt.setSecure(true);
        jwt.setHttpOnly(true);
        response.addCookie(jwt);
        response.sendRedirect("/");
        return "login ok";
    }

    // Affichage formulaire de login
    @GetMapping("/auth/logout")
    @ResponseBody
    public String login_perform(HttpServletResponse response, Model model) throws IOException {
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

    // Affichage formulaire de login
    @GetMapping("/auth/role")
    @ResponseBody
    public String check_role(@CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(service, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        return login.getRole();
    }

    // Affichage formulaire d'inscription
    @GetMapping("/auth/signup")
    public String sign_up(Model model) {
        return "signup_form";
    }


    // Process du formulaire d'inscription
    @PostMapping("/auth/signup_perform")
    @ResponseBody
    public String signup_perform(@ModelAttribute T t, HttpServletResponse response, Model model) throws IOException {

        return "signup ok";
    }

}
