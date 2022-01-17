package com.amt.app;

import com.amt.app.entities.Article;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ArticleTest {

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
        Float price = 100f;

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

    @Test
    void ArticleEquals(){
        Article article1 = new Article();
        String name1 = "article1";
        article1.setName(name1);

        Article article2 = new Article();
        String name2 = "article2";
        article2.setName(name2);

        assertFalse(article1.equals(article2));
        article2.setName(name1);
        assertTrue(article1.equals(article2));
    }
}
