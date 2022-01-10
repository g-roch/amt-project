

package com.amt.app.repository;

import com.amt.app.entities.Article;
import com.amt.app.entities.Cart;
import com.amt.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query(value = "SELECT * FROM cart c WHERE c.user_id = ?1", nativeQuery = true)
    List<Cart> findCartsByUserIdCustom(int userId);

    @Query(value = "SELECT * FROM cart c WHERE c.user_id = ?1 AND c.article_id = ?2", nativeQuery = true)
    Cart findCartByUserIdAndArticleIdCustom(int userId, int articleId);

    @Query(value = "SELECT quantity FROM cart c WHERE c.user_id = ?1 AND c.article_id = ?2", nativeQuery = true)
    int findQuantityByUserIdAndArticleIdCustom(int userId, int articleId);

    @Modifying
    @Query(value = "DELETE FROM cart where user_id = ?1", nativeQuery = true)
    void emptyCart(int userId);

    @Modifying
    @Query(value = "UPDATE cart SET quantity = ?1 WHERE user_id = ?2 AND article_id = ?3", nativeQuery = true)
    void updateCart(int quantity, int userId, int articleId);
}