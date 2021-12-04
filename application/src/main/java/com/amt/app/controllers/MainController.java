package com.amt.app.controllers;

import com.amt.app.auth.Provider;
import com.amt.app.auth.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class MainController {

    @GetMapping("/")
    public String showHome(Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider("HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);
        return "index";
    }

    @GetMapping("/admin")
    public String showAdmin(Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider("HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        System.out.println("role: " + login.getRole());
        String return_page = "";

        //Si l'utilisateur n'a pas le rôle administrateur il est redirigé sur une page d'erreur
        if(login.getRole() == "guest" || login.getRole() == "user"){
            model.addAttribute("error_message", "Vous n'avez pas les droits nécessaires pour accéder à cette page");
            return_page = "error";
        }else{
            return_page = "admin";
        }
        return return_page;
    }

}
