package com.amt.app.controllers;


import com.amt.app.entities.Article;
import com.amt.app.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService service;

    // Affichage de tous les articles disponibles
    @GetMapping("/articles")
    public String showArticles(Model model){

        List<Article> listArticles = service.listAll();
        model.addAttribute("listArticles", listArticles);

        return "articles";
    }

    // Affichage d'un article selon son ID
    @GetMapping("/article/{id}")
    public String showArticleById(@PathVariable long id, Model model){

        Article article =  service.get(id);
        model.addAttribute("article", article);

        return "article";
    }



}
