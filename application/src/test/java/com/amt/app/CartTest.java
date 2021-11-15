package com.amt.app;

import com.amt.app.entities.Article;
import com.amt.app.entities.Cart;
import com.amt.app.entities.Category;
import com.amt.app.entities.User;
import com.amt.app.repository.ArticleRepository;
import com.amt.app.repository.CartRepository;
import com.amt.app.repository.CategoryRepository;
import com.amt.app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Assertions;
import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@DataJpaTest //la in-memory database est partagée entre tous les tests annotés de @DataJpaTest
public class CartTest {

    //@Autowired private EntityManager entityManager;
    @Autowired private CartRepository cart;
    @Autowired private UserRepository user;
    @Autowired private CategoryRepository category;
    @Autowired private ArticleRepository article;

    @Test
    public void test(){
        Article piano = new Article("Piano", 520, "Piano a queue","f1.png", 5);
        Article guitare = new Article("Guitarre", 80, "Gibson","f1.png", 10);
        Article flute = new Article("Flute",140 , "Flute traversiere","f1.png", 15);
        article.save(piano);
        article.save(guitare);
        article.save(flute);

        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user.save(user1);
        user.save(user2);
        user.save(user3);

        Category flutes = new Category("flutes");
        Category vents = new Category("instrumentsAVent");
        Category pianos = new Category("pianos");
        Category guitares = new Category("guitares");
        Category cordes = new Category("cordes");
        Category percussions = new Category("percussions");
        category.save(flutes);
        category.save(vents);
        category.save(pianos);
        category.save(guitares);
        category.save(cordes);
        category.save(percussions);

        Assertions.assertEquals(user.findById(1), user1);
    }

}
