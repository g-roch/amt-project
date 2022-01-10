/**
 * Manage actions done by an administrator (update articles & categories)
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gabriel Roch, Christian Zaccaria
 */

package com.amt.app.controllers;

import com.amt.app.auth.Provider;
import com.amt.app.auth.User;
import com.amt.app.entities.Article;
import com.amt.app.entities.Category;
import com.amt.app.service.ArticleService;
import com.amt.app.service.CategoryService;
import com.amt.app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private ArticleService articleService;
    private CategoryService categoryService;

    public AdminController(UserService userService, ArticleService articleService, CategoryService categoryService){
        this.articleService = articleService;
        this.userService = userService;
        this.categoryService = categoryService;
    }


    /**
     * Filter articles on administrator page
     * @return page to display
     * @param filter_value selected filter value
     */
    @RequestMapping(method = RequestMethod.POST, params = "filter")
    public String updateArticles(@RequestParam(value = "filter_value") String filter_value, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        //By default filter display every articles otherwise display filtered list
        List<Article> listArticles = articleService.listAll();
        if(filter_value.equals("all")){
            model.addAttribute("listArticles", listArticles);
        }else{
            List<Article> filteredList = new ArrayList<Article>();
            for(Article article: listArticles){
                for(Category category : article.getCategories())
                    if(category.getName().equals(filter_value)){
                        filteredList.add(article);
                    }
            }
            model.addAttribute("listArticles", filteredList);
        }

        //Add every category linked to an article, used to show available categories to filter
        List<Category> listCategoriesFilter = categoryService.getAllCategoriesLinkedToArticles();
        model.addAttribute("listCategoriesFilter", listCategoriesFilter);

        //Add every category, used to add categories to articles
        List<Category> listCategories = categoryService.listAll();
        model.addAttribute("listCategories", listCategories);

        //Add selected filter value
        model.addAttribute("filterChoice", filter_value);

        return "admin";
    }

    /**
     * Update categories of an article
     * @return page to display
     * @param articleId id of article
     * @param categories list of categories to update
     */
    @RequestMapping(method = RequestMethod.POST, params = "updateCat")
    public String updateArticleCategorie(@RequestParam(value = "articleId") int articleId,
                                         @RequestParam(value = "categorie" , required = false) List<Category> categories,
                                         Model model) throws IOException {

        boolean isValid = true;
        //check if at least one category is checked
        if(categories == null){
            model.addAttribute("errorMessage", "Il faut au moins une catégorie.");
            isValid = false;
        }

        Article article = articleService.get(articleId);

        //Check if article already has one of the categories selected. If it has, an error occurs.
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

        //Update categories of an article
        // Update articles contained in a category
        if(isValid){
            article.setCategories(categories);
            articleService.addArticle(article);

            List<Category> tmpCategories = new ArrayList<>();
            for(Category category : categories){
                category.addOneArticle(article);
                tmpCategories.add(category);
            }
            for(Category category : tmpCategories){
                categoryService.addCategory(category);
            }
        }

        //Add every article, needed by returned page
        List<Article> listArticles = articleService.listAll();
        model.addAttribute("listArticles", listArticles);

        //Add every category linked to an article, used to show available categories to filter
        List<Category> listCategoriesFilter = categoryService.getAllCategoriesLinkedToArticles();
        model.addAttribute("listCategoriesFilter", listCategoriesFilter);

        //Add every category, used to add categories to articles
        List<Category> listCategories = categoryService.listAll();
        model.addAttribute("listCategories", listCategories);

        return "admin";
    }

    /**
     * Display administrator page
     * @return page to display
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showAdmin(Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);
        String return_page = "";

        //If user is not an admin an error occurs
        if(!login.getRole().equals("admin")){
            model.addAttribute("error_message", "Vous n'avez pas les droits nécessaires pour accéder à cette page");
            return_page = "error";
        }else{

            List<Article> listArticles = articleService.listAll();
            model.addAttribute("listArticles", listArticles);

            List<Category> listCategoriesFilter = categoryService.getAllCategoriesLinkedToArticles();
            model.addAttribute("listCategoriesFilter", listCategoriesFilter);
            List<Category> listCategories = categoryService.listAll();
            model.addAttribute("listCategories", listCategories);

            return_page = "admin";
        }
        return return_page;
    }


}
