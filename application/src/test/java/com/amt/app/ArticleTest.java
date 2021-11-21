package com.amt.app;

import com.amt.app.controllers.ArticleController;
import com.amt.app.entities.Article;
import com.amt.app.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Article.class)
public class ArticleTest {

    @Autowired
    private ArticleController controller;
    @Autowired
    private ArticleService service;

    /*
    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }
     */

    @Test
    void ArticleName(){
        Article article = new Article();
        String name = "article1";

        article.setName(name);
        assertEquals(article.getName(), name);
    }

    @Test
    void ArticleDescription(){
        Article article = new Article();
        String description = "test";

        article.setDescription(description);
        assertEquals(article.getDescription(), description);
    }

    @Test
    void ArticlePrice(){
        Article article = new Article();
        float price = 100;

        article.setPrice(price);
        assertEquals(article.getPrice(), price);
    }

    @Test
    void ArticleStock(){
        Article article = new Article();
        int stock = 1;

        article.setStock(stock);
        assertEquals(article.getStock(), stock);
    }

    @Test
    void ArticleId(){
        int id = 1;
        Article article = new Article();

        article.setId(id);
        assertEquals(article.getId(), id);
    }

    @Test
    void ArticleImage(){

        String image = "image1.png";
        Article article = new Article();
        article.setImage(image);
        assertEquals(article.getImage(), image);
    }




}
