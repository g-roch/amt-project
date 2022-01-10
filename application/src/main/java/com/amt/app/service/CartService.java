/**
 * Link the Cart entity and its custom requests to the database
 * @see CartController.java, Cart.java, CartRepository.java
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gabriel Roch, Christian Zaccaria
 */
package com.amt.app.service;

import com.amt.app.entities.Article;
import com.amt.app.entities.Cart;
import com.amt.app.entities.User;
import com.amt.app.repository.ArticleRepository;
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

    private CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addCart(Cart cart) {
        cartRepository.save(cart);
    }

    public void removeCart(Cart cart){
        cartRepository.delete(cart);
    }

    public List<Cart> findCartsByUserId(int userId){
        return cartRepository.findCartsByUserIdCustom(userId);
    }

    public Cart findCartByUserIdAndArticleId(int userId, int articleId) {
        return cartRepository.findCartByUserIdAndArticleIdCustom(userId, articleId);
    }

    public void emptyCart(int userId){
        cartRepository.emptyCart(userId);
    }

    public void updateCart(int quantity, int userId, int articleId){
        cartRepository.updateCart(quantity, userId, articleId);
    }
}