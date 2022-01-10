/**
 * Manage requests to the database linked to articles
 * @see ArticleController.java, Article.java, ArticleService.java
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gabriel Roch, Christian Zaccaria
 */

package com.amt.app.repository;

import com.amt.app.entities.Article;
import com.amt.app.entities.Cart;
import com.amt.app.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    /**
     * Get Ids of articles contained in a category
     * @param categoryId id of category
     * @return List of articles'ids
     */
    @Query(value = "SELECT articleId FROM article_category WHERE categoryId = ?1",nativeQuery = true)
    List<Integer> getArticlesIdByCategoryId(int categoryId);

    /**
     * Get Stock of an article selected by its name
     * @param name name of article
     * @return Stock of article
     */
    @Query(value = "SELECT stock FROM article a WHERE a.name = ?1", nativeQuery = true)
    int findStockByArticleName(String name);

    /**
     * Get id of an article selected by its name
     * @param name of article
     * @return id of article
     */
    @Query(value = "SELECT id FROM article a WHERE a.name = ?1", nativeQuery = true)
    int findIdByArticleName(String name);
}
