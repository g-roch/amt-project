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

    @GetMapping("/article")
    public String showArticle(Model model){

        List<Article> listArticles = service.listAll();
        model.addAttribute("listArticles", listArticles);

        return "article";
    }

    /*
    @PostMapping("/addArticle")
    public Article addArticle(@RequestBody Article article){
        return service.saveArticle(article);
    }


    @PostMapping("/addArticles")
    public List<Article> addArticle(@RequestBody List<Article> articles){
        return service.saveArticles(articles);
    }


    @GetMapping("/articles")
    public List<Article> findAllArticles(){
        return service.getArticles();
    }

    @GetMapping("/article/{id}")
    public Article findArticleById(@PathVariable int id){
        return service.getArticleById(id);
    }

    @PutMapping("/update")
    public Article updateArticle(@RequestBody Article article){
        return service.updateArticle(article);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id){
        return service.deleteArticle(id);
    }
 */


}
