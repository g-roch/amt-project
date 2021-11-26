package com.amt.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showHome(){
        return "index";
    }

    @GetMapping("/admin")
    public String showAdmin(){
        return "admin";
    }

}
