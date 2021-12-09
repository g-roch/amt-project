package com.amt.app.controllers;

import com.amt.app.auth.Provider;
import com.amt.app.auth.User;
import com.amt.app.entities.Article;
import com.amt.app.entities.Category;
import com.amt.app.service.ArticleService;
import com.amt.app.service.CategoryService;
import com.amt.app.service.UserService;
import com.amt.app.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.xml.catalog.Catalog;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class CategoryController {


    @Autowired
    private CategoryService service;

    @Autowired
    private UserService userService;


    // Formulaire pour la création de categorie
    @GetMapping("/createCategory")
    public String showCreateCategory(Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        System.out.println("role: " + login.getRole());
        String return_page = "";

        //Si l'utilisateur n'a pas le rôle administrateur il est redirigé sur une page d'erreur
        if(!login.getRole().equals("admin")){
            model.addAttribute("error_message", "Vous n'avez pas les droits nécessaires pour accéder à cette page");
            return_page = "error";
        }else{
            Category category = new Category();
            model.addAttribute("category", category);

            return_page = "category_formular";
        }
        return return_page;
    }

    @RequestMapping(value = "/createCategory", method = RequestMethod.POST)
    public String submitFormCategory(@Valid Category category, BindingResult result, Model model ) {

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "category_formular";
        }

        // Vérification si elle existe déjà
        List<Category> categories = service.listAll();
        for(Category c : categories){
            if(c.getName().equals(category.getName())){
                model.addAttribute("categoryExists", true);
                return "category_formular";
            }
        }

        service.addCategory(category);

        return "admin";
    }






}
