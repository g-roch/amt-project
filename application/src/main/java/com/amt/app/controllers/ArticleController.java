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
import java.io.IOException;
import java.util.*;

@Controller
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;


    // Affichage de tous les articles disponibles pour la plebes
    @GetMapping("/articles")
    public String showArticles(Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        //Envoyer tous les articles pour l'affichage
        List<Article> listArticles = articleService.listAll();
        model.addAttribute("listArticles", listArticles);

        //Envoyer toutes les catégories pour le filtre
        List<Category> listCategories = categoryService.listAll();
        model.addAttribute("listCategories", listCategories);

        return "articles";
    }

    // Filtre sur les article
    @PostMapping("/articles")
    public String updateArticles(@RequestParam(value = "filter_value") String filter_value, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        List<Article> listArticles = articleService.listAll();
        List<Article> filteredList = new ArrayList<Article>();

        for(Article article: listArticles){
            for(Category category : article.getCategories())
            if(category.getName().equals(filter_value)){
                filteredList.add(article);
            }
        }

        model.addAttribute("listArticles", filteredList);

        //Envoyer toutes les catégories pour le filtre
        List<Category> listCategories = categoryService.listAll();
        model.addAttribute("listCategories", listCategories);

        return "articles";
    }

    // Affichage d'un article selon son ID
    @GetMapping("/article/{id}")
    public String showArticleById(@PathVariable int id, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        Article article =  articleService.get(id);
        model.addAttribute("article", article);

        return "article";
    }

    // Formulaire pour la création d'article
    @GetMapping("/createArticle")
    public String showCreateArticle(Model model,@CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        System.out.println("role: " + login.getRole());
        String return_page = "";

        /*
        Article article = new Article();
        model.addAttribute("article", article);
        return_page = "article_formular";
        return return_page;
        */

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
    public String submitFormArticle(@Valid Article article, BindingResult result, Model model ,
                             @RequestParam("file") MultipartFile multipartFile) throws IOException {

        //@Valid control les entrées de l'utilisateurs selon les annotation dans l'entité
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "article_formular";
        }

        // Vérification si le nom de l'article existe déjà, si c'est le cas on l'affiche.
        List<Article> articles = articleService.listAll();
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
        Article savedArticle = articleService.addArticle(article);

        //Upload de l'image uniquement si il a mis une image
        if(!isDefaultImage){
            String uploadDir = "article-photos/" + savedArticle.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        return "article_success";
    }
}
