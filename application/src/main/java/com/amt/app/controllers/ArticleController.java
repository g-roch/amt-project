/**
 * Manage actions on an article (display, create, add to cart)
 * @see Article.java, ArticleRepository.java, ArticleService.java
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gabriel Roch, Christian Zaccaria
 */

package com.amt.app.controllers;

import com.amt.app.auth.Provider;
import com.amt.app.auth.User;
import com.amt.app.entities.Article;
import com.amt.app.entities.Cart;
import com.amt.app.entities.Category;
import com.amt.app.service.ArticleService;
import com.amt.app.service.CartService;
import com.amt.app.service.CategoryService;
import com.amt.app.service.UserService;
import com.amt.app.utils.FileUploadUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CartService cartService;

    public ArticleController(ArticleService articleService, UserService userService, CategoryService categoryService, CartService cartService) {
        this.articleService = articleService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.cartService = cartService;
    }

    /**
     * Filter Display articles page
     * @return page to display
     */
    @GetMapping("/articles")
    public String showArticles(Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8089/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        //Add every article to display
        List<Article> listArticles = articleService.listAll();
        model.addAttribute("listArticles", listArticles);

        //Add every category to filter
        List<Category> listCategories = categoryService.getAllCategoriesLinkedToArticles();
        model.addAttribute("listCategories", listCategories);

        return "articles";
    }

    /**
     * Add one article to cart
     * @return page to display
     * @param id of article
     */
    @GetMapping(value = "/articles", params = {"id"})
    public String addArticleToCart(@RequestParam(value = "id") int id, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt, HttpSession session) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8089/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        Article article =  articleService.get(id);
        int stock = articleService.findStockByArticleName(article.getName());

        model.addAttribute("article", article);
        int quantity = 1;
        //Add article as session attribute with stock set to quantity selected by user
        if(login.getRole().equals("guest")){
            //if article is not already in cart
            if(session.getAttribute(article.getName()) == null){
                article.setStock(quantity);
            }else{
                Article existingArticle = (Article) session.getAttribute(article.getName());
                quantity += existingArticle.getStock();
                if(stock < quantity){
                    model.addAttribute("error_message", "Quantité d'articles voulue plus grande que le nombre disponible en stock!");
                    return "error";
                }
                article.setStock(quantity);
                session.removeAttribute(article.getName());
            }
            session.setAttribute(article.getName(),article);
        }else{
            //Add cart into the db
            com.amt.app.entities.User user = new com.amt.app.entities.User(login.getId());

            //if article is not already in cart
            if(cartService.findCartByUserIdAndArticleId(user.getId(),article.getId()) == null){
                Cart cart = new Cart(article,user,quantity);
                cartService.addCart(cart);
            }else{
                Cart existingCart = cartService.findCartByUserIdAndArticleId(user.getId(),article.getId());
                quantity += existingCart.getQuantity();

                if(stock < quantity){
                    model.addAttribute("error_message", "Quantité d'articles voulue plus grande que le nombre disponible en stock!");
                    return "error";
                }
                cartService.updateCart(quantity,user.getId(),article.getId());
            }
        }
        //Used to display the quantity of selected article in cart on the success page
        article.setStock(quantity);
        return "article_add_to_cart_success";
    }

    /**
     * Filter articles displayed
     * @return page to display
     * @param filter_value selected filter value
     */
    @PostMapping("/articles")
    public String updateArticles(@RequestParam(value = "filter_value") String filter_value, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8089/");
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

        //Add every category, used to add categories to articles
        List<Category> listCategories = categoryService.getAllCategoriesLinkedToArticles();
        model.addAttribute("listCategories", listCategories);

        //Add selected filter value
        model.addAttribute("filterChoice", filter_value);

        return "articles";
    }

    /**
     * Display informations about an article
     * @return page to display
     * @param id selected article id
     */
    @GetMapping("/article/{id}")
    public String showArticleById(@PathVariable int id, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8089/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        Article article =  articleService.get(id);
        model.addAttribute("article", article);

        return "article";
    }

    /**
     * Add an article to cart
     * @return page to display
     * @param quantity quantity of article to add
     * @param id id of article to add
     */
    @PostMapping(value="/article/{id}")
    public String addArticleToCart(@RequestParam(value = "quantity") int quantity,@PathVariable int id,Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt, HttpSession session) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8089/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        Article article =  articleService.get(id);
        int stock = article.getStock();
        model.addAttribute("article", article);

        if(quantity == 0){
            model.addAttribute("error_message", "Vous ne pouvez pas ajouter 0 article à votre panier!");
            return "error";
        }else if(stock < quantity){
            model.addAttribute("error_message", "Quantité d'articles voulue plus grande que le nombre disponible en stock!");
            return "error";
        }

        //Add article as session attribute with stock set to quantity selected by user
        if(login.getRole().equals("guest")){
            //if article is not already in cart
            if(session.getAttribute(article.getName()) == null){
                article.setStock(quantity);
            }else{
                Article existingArticle = (Article) session.getAttribute(article.getName());
                quantity += existingArticle.getStock();
                if(stock < quantity){
                    model.addAttribute("error_message", "Quantité d'articles voulue plus grande que le nombre disponible en stock!");
                    return "error";
                }
                article.setStock(quantity);
                session.removeAttribute(article.getName());
            }
            session.setAttribute(article.getName(),article);
        }else{
            //Add cart into the db
            com.amt.app.entities.User user = new com.amt.app.entities.User(login.getId());

            //if article is not already in cart
            if(cartService.findCartByUserIdAndArticleId(user.getId(),article.getId()) == null){
                Cart cart = new Cart(article,user,quantity);
                cartService.addCart(cart);
            }else{
                Cart existingCart = cartService.findCartByUserIdAndArticleId(user.getId(),article.getId());
                quantity += existingCart.getQuantity();

                if(stock < quantity){
                    model.addAttribute("error_message", "Quantité d'articles voulue plus grande que le nombre disponible en stock!");
                    return "error";
                }

                cartService.updateCart(quantity,user.getId(),article.getId());
            }
        }

        //Used to display the quantity of selected article in cart on the success page
        article.setStock(quantity);
        return "article_add_to_cart_success";
    }
    /**
     * Display formular to create article
     * @return page to display
     */
    @GetMapping("/createArticle")
    public String showCreateArticle(Model model,@CookieValue(name = "jwt", defaultValue = "") String jwt) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8089/");
        User login = provider.login(jwt);
        System.out.println("role: " + login.getRole());
        String return_page = "";

        //If user doesnt have admin rights an error occurs
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

    /**
     * Create a new article
     * @return page to display
     * @param article created article
     */
    @RequestMapping(value = "/createArticle", method = RequestMethod.POST)
    public String submitFormArticle(@Valid Article article, BindingResult result, Model model ,
                                    @RequestParam("file") MultipartFile multipartFile) throws IOException {

        //@Valid control user inputs according to entity annotations
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "article_formular";
        }

        // Check if an article already exists with the same name
        List<Article> articles = articleService.listAll();
        Article exists = null;
        for(Article a : articles){
            if(a.equals(article)){
                exists = a;
            }
        }
        model.addAttribute("articleExistant", exists);
        if (exists != null) return "article_formular";

        // Tuto to upload a file
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

        model.addAttribute("sucessfulMessage", "Article crée avec succès.");
        Article savedArticle = articleService.addArticle(article);

        //Upload image in the case of an image is provided
        if(!isDefaultImage){
            String uploadDir = "article-photos/" + savedArticle.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        return "article_formular";
    }
}
