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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;//TODO NGY remove unused import statement

import javax.validation.Valid;
import java.io.IOException;//TODO NGY remove unused import statement
import java.util.List;

@Controller
@RequestMapping("/createCategory")
public class CategoryController {


    @Autowired //TODO NGY Field injection not recommended
    private CategoryService categoryService;

    @Autowired //TODO NGY Field injection not recommended
    private UserService userService;

    @Autowired //TODO NGY Field injection not recommended
    private ArticleService articleService;

    // Affichage de base
    @RequestMapping(method = RequestMethod.GET)
    public String showCreateCategory(Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);
        String return_page = "";

        //Si l'utilisateur n'a pas le rôle administrateur il est redirigé sur une page d'erreur
        if(!login.getRole().equals("admin")){
            model.addAttribute("error_message", "Vous n'avez pas les droits nécessaires pour accéder à cette page");
            return_page = "error";
        }else{
            //Envoyer les différents attributs nécessaires à l'affichage
            Category category = new Category();
            model.addAttribute("category", category);
            List<Category> categories = categoryService.listAll();
            model.addAttribute("categories", categories);

            return_page = "category_formular";
        }
        return return_page;
    }

    //Delete une catégorie
    @RequestMapping(method = RequestMethod.POST, params = "delete")
    public String deleteCategory(@RequestParam("categoryId") int categoryId, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        Category deletingCategory = categoryService.get(categoryId);

        //Check si la categorie a un article
        if(!deletingCategory.getArticles().isEmpty()){
            Category category = categoryService.get(categoryId);
            model.addAttribute("category", category);
            List<Article> listArticles = articleService.getArticlesByCategoryId(categoryId);
            model.addAttribute("listArticles", listArticles);
            return "confirm_delete_category";
        }else{
            categoryService.delete(categoryId);
            model.addAttribute("sucessfulMessage", "Catégorie supprimée avec succès.");
        }

        //Envoyer les différents attributs nécessaires à l'affichage
        Category category = new Category();
        model.addAttribute("category", category);
        List<Category> categories = categoryService.listAll();
        model.addAttribute("categories", categories);

        return "category_formular";
    }

    @RequestMapping(method = RequestMethod.POST, params = "confirmDelete")
    public String confirmDeleteCategory(@RequestParam("categoryId") int categoryId, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        categoryService.delete(categoryId);

        Category category = new Category();
        model.addAttribute("category", category);
        List<Category> categories = categoryService.listAll();
        model.addAttribute("categories", categories);

        return "category_formular";
    }

    @RequestMapping(method = RequestMethod.POST, params = "cancelDelete")
    public String cancelDeleteCategory(@RequestParam("categoryId") int categoryId, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        Category category = new Category();
        model.addAttribute("category", category);
        List<Category> categories = categoryService.listAll();
        model.addAttribute("categories", categories);

        return "category_formular";
    }

    // Créer une catégorie
    @RequestMapping(method = RequestMethod.POST, params = "create")
    public String submitFormCategory(@Valid Category category, BindingResult result, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        if (result.hasErrors()) {
            List<Category> listCategories = categoryService.listAll();
            model.addAttribute("categories", listCategories);
            System.out.println(result.getAllErrors());
            return "category_formular";
        }

        // Vérification si elle existe déjà
        List<Category> categories = categoryService.listAll();
        for(Category c : categories){
            if(c.equals(category)){
                List<Category> listCategories = categoryService.listAll();
                model.addAttribute("categories", listCategories);
                model.addAttribute("categoryExists", true);
                return "category_formular";
            }
        }

        categoryService.addCategory(category);

        //Envoyer les différents attributs nécessaires à l'affichage
        model.addAttribute("sucessfulMessage", "Catégorie crée avec succès.");
        List<Category> listCategories = categoryService.listAll();
        model.addAttribute("categories", listCategories);

        return "category_formular";
    }
}
