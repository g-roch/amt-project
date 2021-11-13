package com.amt.app.controllers;


import com.amt.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



@Controller
public class UserController {

    @Autowired
    private UserService service;
}
