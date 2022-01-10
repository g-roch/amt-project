/**
 * Manage requests to the database linked to carts
 * @see CartController.java, Cart.java, CartService.java
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gabriel Roch, Christian Zaccaria
 */


package com.amt.app.repository;

import com.amt.app.entities.Article;
import com.amt.app.entities.Cart;
import com.amt.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CartRepository extends JpaRepository<Cart, Integer> {
    /**
     * Get carts of a user
     * @param userId id of user
     * @return List of carts
     */
    @Query(value = "SELECT * FROM cart c WHERE c.user_id = ?1", nativeQuery = true)
    List<Cart> findCartsByUserIdCustom(int userId);

    /**
     * Get specific article from a cart of a specific user
     * @param userId id of user
     * @param articleId id of article
     * @return Cart
     */
    @Query(value = "SELECT * FROM cart c WHERE c.user_id = ?1 AND c.article_id = ?2", nativeQuery = true)
    Cart findCartByUserIdAndArticleIdCustom(int userId, int articleId);

    /**
     * Delete cart of a user
     * @param userId id of user
     */
    @Modifying
    @Query(value = "DELETE FROM cart where user_id = ?1", nativeQuery = true)
    void emptyCart(int userId);

    /**
     * Update quantity of specific article from a cart of a specific user
     * @param quantity quantity to update
     * @param userId id of user
     * @param articleId id of article
     */
    @Modifying
    @Query(value = "UPDATE cart SET quantity = ?1 WHERE user_id = ?2 AND article_id = ?3", nativeQuery = true)
    void updateCart(int quantity, int userId, int articleId);
}