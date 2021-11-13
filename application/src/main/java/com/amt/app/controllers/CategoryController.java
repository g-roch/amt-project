package com.amt.app.controllers;


import com.amt.app.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



@Controller
public class CategoryController {

    @Autowired
    private CategoryService service;
}
