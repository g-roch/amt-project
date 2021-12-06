package com.amt.app.controllers;


import com.amt.app.auth.Provider;
import com.amt.app.auth.User;
import com.amt.app.entities.Article;
import com.amt.app.repository.ArticleRepository;
import com.amt.app.service.ArticleService;
import com.amt.app.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

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

    @PostMapping("/articles")
    public String updateArticles(@RequestParam(value = "filter_value") int filter_value, Model model){

        List<Article> listArticles = service.listAll();
        List<Article> removeList = new ArrayList<Article>();

        if(filter_value != 0){
            for(Article article: listArticles){
                if(article.getPrice() == null || article.getPrice() < filter_value){
                    removeList.add(article);
                }
            }
            listArticles.removeAll(removeList);
        }

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
    public String createArticle(Model model,@CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider("HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        System.out.println("role: " + login.getRole());
        String return_page = "";

        //Si l'utilisateur n'a pas le rôle administrateur il est redirigé sur une page d'erreur
        if(!login.getRole().equals("admin")){
            model.addAttribute("error_message", "Vous n'avez pas les droits nécessaires pour accéder à cette page");
            return_page = "error";
        }else{
            Article article = new Article();
            model.addAttribute("article", article);

            return_page = "article_formular";
        }
        return return_page;
    }

    // Success page quand l'article à été crée
    @RequestMapping(value = "/createArticle", method = RequestMethod.POST)
    public String submitForm(@Valid Article article, BindingResult result, Model model ,
                             @RequestParam("file") MultipartFile multipartFile) throws IOException {

        //@Valid control les entrées de l'utilisateurs selon les annotation dans l'entité
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "article_formular";
        }

        // Vérification si le nom de l'article existe déjà, si c'est le cas on l'affiche.
        List<Article> articles = service.listAll();
        Article exists = null;
        for(Article a : articles){
            if(a.getName().equals(article.getName())){
                exists = a;
            }
        }
        model.addAttribute("articleExistant", exists);
        if (exists != null) return "article_formular";

        // Tuto pour l'upload de fichier mais ça ne fonctionne pas...
        // now it does ==> spring.servlet.multipart.enabled=true dans application properties
        // https://www.codejava.net/frameworks/spring-boot/spring-boot-file-upload-tutorial
        String fileName;
        boolean isDefaultImage;
        if(multipartFile.isEmpty()){
            fileName = "default.png";
            isDefaultImage = true;
        }else{
            fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            isDefaultImage = false;
        }

        article.setImage(fileName);

        model.addAttribute("article", article);
        Article savedArticle = service.addArticle(article);

        //Upload de l'image uniquement si il a mis une image
        if(!isDefaultImage){
            String uploadDir = "article-photos/" + savedArticle.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        return "article_success";
    }
}
