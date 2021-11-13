package com.amt.app;

import com.amt.app.controllers.CartController;
import com.amt.app.entities.Article;
import com.amt.app.entities.Cart;
import com.amt.app.entities.CartId;
import com.amt.app.entities.User;
import com.amt.app.repository.ArticleRepository;
import com.amt.app.repository.CartRepository;
import com.amt.app.repository.UserRepository;
import com.amt.app.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CartTest {

    @Autowired
    private CartController controller;
    @Autowired
    private CartService service;

    private ArticleRepository articleRepository;
    private UserRepository userRepository;
    private CartRepository cartRepository;


    @Test
    void test(){
        userRepository.save(new User());
        userRepository.save(new User());
        userRepository.save(new User());

        articleRepository.save(new Article("Piano",100,"Piano à queue","application/src/main/resources/static/images/f1.png", 20));
        articleRepository.save(new Article("Guitarre",50,"Gibson","application/src/main/resources/static/images/f1.png", 10));
        articleRepository.save(new Article("Flute",25,"Flute traversière","application/src/main/resources/static/images/f1.png", 5));

        cartRepository.save(new Cart(new CartId(1,1),1));
        cartRepository.save(new Cart(new CartId(1,2),2));
        cartRepository.save(new Cart(new CartId(1,3),3));

        cartRepository.save(new Cart(new CartId(2,1),1));
        cartRepository.save(new Cart(new CartId(2,2),2));

        cartRepository.save(new Cart(new CartId(3,3),5));

        List<Cart> carts = cartRepository.findAll();

    }


}
