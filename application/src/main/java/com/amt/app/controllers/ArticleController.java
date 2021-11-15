package com.amt.app.controllers;


import com.amt.app.entities.Article;
import com.amt.app.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/* Model sert à transmettre une variable dans la page html */

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

    // Formulaire pour la création d'article
    @GetMapping("/createArticle")
    public String createArticle(Model model){
        Article article = new Article();
        model.addAttribute("article", article);

        return "article_formular";
    }

    // Success page quand l'article à été crée
    @PostMapping("/createArticle")
    public String submitForm(@Valid Article article, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "article_formular";
        }


        List<Article> articles = service.listAll();
        Article existantArticle = null;

        for(Article a : articles){
            //Vérification pour augmenter le stock si l'article existe deja
            if(a.getName().equals(article.getName())){
                int actualStock = a.getStock();
                existantArticle = service.get(a.getId());
                existantArticle.setStock(actualStock + article.getStock());
                break;
            }
        }

        Article correctArticle;
        if(existantArticle == (null)){
            correctArticle = article;
        }else{
            correctArticle = existantArticle;
        }

        model.addAttribute("article", correctArticle);
        service.addArticle(correctArticle);

        return "article_success";
    }



}
