package com.amt.app.controllers;

import com.amt.app.auth.Provider;
import com.amt.app.auth.User;
import com.amt.app.entities.Article;
import com.amt.app.entities.Category;
import com.amt.app.service.ArticleService;
import com.amt.app.service.CategoryService;
import com.amt.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;

    // Filtre pour l'admin
    @RequestMapping(method = RequestMethod.POST, params = "filter")
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
        List<Category> listCategoriesFilter = categoryService.getAllCategoriesLinkedToArticles();
        model.addAttribute("listCategoriesFilter", listCategoriesFilter);

        List<Category> listCategories = categoryService.listAll();
        model.addAttribute("listCategories", listCategories);

        return "admin";
    }

    // Changement de categorie sur un article
    @RequestMapping(method = RequestMethod.POST, params = "updateCat")
    public String updateArticleCategorie(@RequestParam(value = "articleId") int articleId,
                                         @RequestParam(value = "categorie" , required = false) List<Category> categories,
                                         Model model) throws IOException {

        boolean isValid = true;
        //check si aucune coche a été checkée
        if(categories == null){
            model.addAttribute("errorMessage", "Il faut au moins une catégorie.");
            isValid = false;
        }

        // Récupérer l'article en question
        Article article = articleService.get(articleId);

        // Vérifier s'il possède déjà au moins une des catégories entrées. Une seule suffit pour envoyer le message
        // d'erreur.
        if(isValid){
            for (Category category : categories) {
                for (int j = 0; j < article.getCategories().size(); j++) {
                    if (category.getName().equals(article.getCategories().get(j).getName())) {
                        model.addAttribute("errorMessage", "Cet article possède déjà la catégorie suivant : "
                                + category.getName());
                        System.out.println("Il a deja cette catégorie");
                        isValid = false;
                        break;
                    }
                }
            }
        }

        // 1. Mettre à jour l'attribut "categories" de articles
        // 2. Mettre à jour l'attribut "articles" dans chaque catégorie
        if(isValid){
            article.setCategories(categories);
            articleService.addArticle(article);

            // Obliger de le faire en deux loops sinon j'avais une ConcurrentModificationException.
            List<Category> tmpCategories = new ArrayList<>();
            for(Category category : categories){
                category.addOneArticle(article);
                tmpCategories.add(category);
            }
            for(Category category : tmpCategories){
                categoryService.addCategory(category);
            }
        }

        // On repart sur la page admin donc on doit passer tous les articles et les catégories.
        List<Article> listArticles = articleService.listAll();
        model.addAttribute("listArticles", listArticles);

        //Envoyer toutes les catégories pour le filtre
        List<Category> listCategoriesFilter = categoryService.getAllCategoriesLinkedToArticles();
        model.addAttribute("listCategoriesFilter", listCategoriesFilter);

        List<Category> listCategories = categoryService.listAll();
        model.addAttribute("listCategories", listCategories);

        return "admin";
    }

    // Affichage de tous les articles disponibles pour les admins
    @RequestMapping(method = RequestMethod.GET)
    public String showAdmin(Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);
        String return_page = "";

        //Si l'utilisateur n'a pas le rôle administrateur il est redirigé sur une page d'erreur
        if(!login.getRole().equals("admin")){
            model.addAttribute("error_message", "Vous n'avez pas les droits nécessaires pour accéder à cette page");
            return_page = "error";
        }else{

            // On repart sur la page admin donc on doit passer tous les articles et les catégories.
            List<Article> listArticles = articleService.listAll();
            model.addAttribute("listArticles", listArticles);

            //Envoyer toutes les catégories pour le filtre
            List<Category> listCategoriesFilter = categoryService.getAllCategoriesLinkedToArticles();
            model.addAttribute("listCategoriesFilter", listCategoriesFilter);
            List<Category> listCategories = categoryService.listAll();
            model.addAttribute("listCategories", listCategories);

            return_page = "admin";
        }
        return return_page;
    }


}
