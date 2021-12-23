package com.amt.app;

import com.amt.app.entities.Article;
import com.amt.app.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CategoryTest {


    @Test
    void CategoryName(){
        Category category = new Category();
        String name = "category";
        category.setName(name);

        assertEquals(category.getName(), name);
    }

    @Test
    void CategoryEquals(){
        Category category1 = new Category();
        String name1 = "category1";
        category1.setName(name1);

        Category category2 = new Category();
        String name2 = "article2";
        category2.setName(name2);

        assertFalse(category1.equals(category2));
        category2.setName(name1);
        assertTrue(category1.equals(category2));
    }

    @Test
    void CategorySetArticlesAndAddOneCategory(){
        Category category = new Category();
        String name = "category";
        category.setName(name);

        Article article = new Article();
        article.setName("article");

        List<Article> articles = new ArrayList<>();
        articles.add(article);

        category.setArticles(articles);

        assertEquals(category.getArticles(), articles);
        assertEquals(category.getArticles().size(), 1);

        Article newArticle = new Article();
        newArticle.setName("newArticle");
        category.addOneArticle(newArticle);

        assertEquals(category.getArticles().size(), 2);
    }






}
