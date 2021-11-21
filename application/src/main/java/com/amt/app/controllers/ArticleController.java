package com.amt.app.controllers;


import com.amt.app.entities.Article;
import com.amt.app.repository.ArticleRepository;
import com.amt.app.service.ArticleService;
import com.amt.app.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/* Model sert à transmettre une variable dans la page html */

@Controller
@Component
public class ArticleController {

    @Autowired
    private ArticleService service;

    @Qualifier("articleService")
    public void setArticleService(ArticleService articleService){
        this.service = articleService;
    }

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
    @RequestMapping(value = "/createArticle", method = RequestMethod.POST)
    public String submitForm(@Valid Article article, BindingResult result, Model model ,
                             @RequestParam("file") MultipartFile multipartFile) throws IOException {

        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "article_formular";
        }

        // Tuto pour l'upload de fichier mais ça ne fonctionne pas...
        // now it does ==> spring.servlet.multipart.enabled=true dans application properties
        // https://www.codejava.net/frameworks/spring-boot/spring-boot-file-upload-tutorial
        String fileName;
        boolean isDefaultImage;
        if(multipartFile.isEmpty()){
            fileName = "default.png";
            isDefaultImage = true;
        }else{
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            isDefaultImage = false;
        }

        article.setImage(fileName);

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
        Article savedArticle = service.addArticle(correctArticle);

        System.out.println(savedArticle.getId());

        if(!isDefaultImage){
            String uploadDir = "article-photos/" + savedArticle.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        return "article_success";
    }



}
