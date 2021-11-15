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
    public String showArticleById(@PathVariable int id, Model model){
        Article article =  service.get(id);
        model.addAttribute("article", article);

        return "article";
    }

    @GetMapping("/createArticle")
    public String createArticle(Model model){
        Article article = new Article();
        model.addAttribute("article", article);

        return "article_formular";
    }

    @PostMapping("/createArticle")
    public String submitForm(@ModelAttribute("article") Article article, Model model) {
        model.addAttribute("article", article);

        List<Article> articles = service.listAll();
        int actualStock = 0;
        Integer lastId = 0;

        for(Article a : articles){
            if(a.getName() == article.getName()){
                actualStock = a.getStock();
            }
            lastId = a.getId();
        }

        if(actualStock >= 1){
            article.setStock(actualStock + 1);
        }else{
            article.setStock(1);
        }

        lastId = new Integer(lastId.intValue() + 1);
        article.setId(lastId);
        service.addArticle(article);

        return "success";
    }



}
