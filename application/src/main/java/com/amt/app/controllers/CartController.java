package com.amt.app.controllers;


import com.amt.app.auth.Provider;
import com.amt.app.auth.User;
import com.amt.app.entities.Article;
import com.amt.app.entities.Cart;
import com.amt.app.service.ArticleService;
import com.amt.app.service.CartService;
import com.amt.app.service.UserService;
import com.amt.app.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

/* Model sert à transmettre une variable dans la page html */

@Controller
public class CartController {

    @Autowired //TODO NGY Field injection not recommended
    private CartService cartService;
    @Autowired //TODO NGY Field injection not recommended
    private UserService userService;
    @Autowired //TODO NGY Field injection not recommended
    private ArticleService articleService;

    private List<Article> getArticles(User login, HttpSession session){
        List<Article> listArticles = new ArrayList<Article>();

        //Si l'utilisateur est un guess, on va chercher les articles du panier dans les attributs de la session
        if(login.getRole().equals("guest")){
            Enumeration<String> attributes = session.getAttributeNames();

            while (attributes.hasMoreElements()) {
                String attribute = (String) attributes.nextElement();
                listArticles.add((Article)session.getAttribute(attribute));
            }
        }else{
            //Si c'est un utilisateur authentifié on va chercher les articles dans la db
            List<Cart> carts = new ArrayList<>();
            com.amt.app.entities.User user = new com.amt.app.entities.User(login.getId());
            carts = cartService.findCartsByUserId(user.getId());
            if(!carts.isEmpty()){
                for(Cart cart : carts){
                    Article article = cart.getArticle();
                    article.setStock(cart.getQuantity());
                    listArticles.add(article);
                }
            }
        }
        return listArticles;
    }

    @GetMapping("/cart")
    public String showArticles(Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt, HttpSession session) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        List<Article> listArticles = getArticles(login,session);
        model.addAttribute("listArticles", listArticles);

        return "cart";
    }

   @GetMapping(value = "/cart", params = {"quantity", "name", "id"})
    public String updateQuantity(@RequestParam(value = "quantity") int quantity, @RequestParam(value = "name") String name, @RequestParam(value = "id") int id, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt, HttpSession session) throws Exception {
       Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
       User login = provider.login(jwt);
       model.addAttribute("login", login);

       int stock = articleService.findStockByArticleName(name);

       if(quantity == 0){
           return deleteCart(name,id,model,jwt,session);
       }else if(stock < quantity){
           model.addAttribute("error_message", "Quantité d'articles entrée plus grande que le nombre disponible en stock!");
           return "error";
       }

       if(login.getRole().equals("guest")){
           Article article = articleService.get(id);
           article.setStock(quantity);
           session.removeAttribute(name);
           session.setAttribute(name, article);
       }else{
            cartService.updateCart(quantity,login.getId(),id);
       }

       List<Article> listArticles = getArticles(login,session);
       model.addAttribute("listArticles", listArticles);

       return "cart";
   }


    @GetMapping(value = "/cart", params = {"name", "id"})
    public String deleteCart(@RequestParam(value = "name") String name, @RequestParam(value = "id") int id, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt, HttpSession session) throws Exception {
       Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
       User login = provider.login(jwt);
       model.addAttribute("login", login);

       System.out.println("testttttttttttttttttt");
       if(login.getRole().equals("guest")){
            session.removeAttribute(name);
       }else{
           cartService.removeCart(cartService.findCartByUserIdAndArticleId(login.getId(),id));
       }

       List<Article> listArticles = getArticles(login,session);
       model.addAttribute("listArticles", listArticles);

       return "cart";
   }

   @PostMapping("/cart")
   public String emptyCart(Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt, HttpSession session) throws Exception {
       Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
       User login = provider.login(jwt);
       model.addAttribute("login", login);

       if(login.getRole().equals("guest")){
           Enumeration<String> attributes = session.getAttributeNames();

           while (attributes.hasMoreElements()) {
               String attribute = (String) attributes.nextElement();
               session.removeAttribute(attribute);
           }
       }else{
            cartService.emptyCart(login.getId());
       }

       List<Article> listArticles = getArticles(login,session);
       model.addAttribute("listArticles", listArticles);

       return "cart";
   }
}
