package com.amt.app.controllers;


import com.amt.app.auth.Provider;
import com.amt.app.auth.User;
import com.amt.app.entities.Article;
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

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/cart")
    public String showArticles(@PathVariable int id, Model model, @CookieValue(name = "jwt", defaultValue = "") String jwt, HttpSession session) throws Exception {
        Provider provider = new Provider(userService, "HS256", "czvFbg2kmvqbcu(7Ux+c", "IICT", "http://127.0.0.1:8081/");
        User login = provider.login(jwt);
        model.addAttribute("login", login);

        String sessionId = session.getId();
        System.out.println("session id: " + sessionId);

        List<Article> listArticles = new ArrayList<Article>();
        Enumeration<String> attributes = session.getAttributeNames();


        while (attributes.hasMoreElements()) {
            String attribute = (String) attributes.nextElement();
            System.out.println("truc " + attribute+" : "+ session.getAttribute(attribute));
        }
        model.addAttribute("listArticles", listArticles);


        Article article =  articleService.get(id);
        model.addAttribute("article", article);

        session.setAttribute(article.getName(),article);

        return "cart";
    }
}
