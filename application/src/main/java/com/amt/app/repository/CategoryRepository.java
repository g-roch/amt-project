/**
 * Manage requests to the database linked to categories
 * @see CategoryController.java, Category.java, CategoryService.java
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gabriel Roch, Christian Zaccaria
 */


package com.amt.app.repository;

import com.amt.app.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Get all ids of categories linked to articles
     * @return List of categories'id
     */
    @Query(value = "SELECT DISTINCT categoryId FROM article_category",nativeQuery = true)
    List<Integer> getAllCategoriesLinkedToArticles();
}
