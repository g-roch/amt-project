package com.amt.app.repository;

import com.amt.app.entities.Article;
import com.amt.app.entities.Cart;
import com.amt.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query(value = "SELECT a.id FROM article a " +
                    "JOIN cart ca ON ca.articleId = a.id " +
            "JOIN users u ON u.id = cart.userId AND u.id = 1", nativeQuery = true)
    List<Integer> findAllItems();

    @Query(value = "SELECT c.articleId FROM cart c WHERE c.userId =1", nativeQuery = true)
    List<Integer> findAllItems2();

}
