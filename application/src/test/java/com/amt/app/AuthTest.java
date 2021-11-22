package com.amt.app;

import com.amt.app.auth.Provider;
import com.amt.app.auth.User;
import com.amt.app.controllers.ArticleController;
import com.amt.app.entities.Article;
import com.amt.app.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthTest {

    //@Autowired
    //private ArticleController controller;
    //@Autowired
    //private ArticleService service;

    @Test
    public void test() throws Exception {
        Provider provider = new Provider("HS256", "secret".getBytes(), "me");
        String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaXNzIjoibWUiLCJpYXQiOjE1MTYyMzkwMjJ9.yH3GHqHRcKKFBDKlbmR2uIOAuj0fVs7nVpO1P9eb5ns";
        User user = provider.login(jwt);
        assertEquals(user.getId(), 1234567890);
    }

    /*
    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

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

        //String default_image = "f1.png";
        String image = "image1.png";
        Article article = new Article();
        //Article article = service.get(1);
        //assertEquals(article.getImage(), default_image);
        article.setImage(image);
        assertEquals(article.getImage(), image);
    }


*/

}
