package com.amt.app.controllers;

import com.amt.app.auth.Provider;
import com.amt.app.auth.User;

import com.amt.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private UserService userService;

    // Affichage de la home page
    @RequestMapping(method = RequestMethod.GET)
    public String showHome(Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);
        return "index";
    }



}
