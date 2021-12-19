package com.amt.app.service;

import com.amt.app.entities.Article;
import com.amt.app.entities.Cart;
import com.amt.app.entities.User;
import com.amt.app.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository repository;

    public void addCart(Cart cart) {
         repository.save(cart);
    }


    public List<Cart> findCartsByUserId(User user){
        return repository.findCartsByUserCustom(user);
    }


}