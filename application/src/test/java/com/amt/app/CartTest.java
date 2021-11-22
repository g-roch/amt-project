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
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Assertions;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest //la in-memory database est partagée entre tous les tests annotés de @DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
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

        //cart user 1
        Cart cart11 = new Cart(piano,user1,2);
        Cart cart12 = new Cart(guitare,user1,3);
        Cart cart13 = new Cart(flute,user1,5);
        cart.save(cart11);
        cart.save(cart12);
        cart.save(cart13);

        //cart user2
        Cart cart21 = new Cart(piano,user2,20);
        Cart cart22 = new Cart(flute, user2, 10);
        cart.save(cart21);
        cart.save(cart22);

        //cart user3

        Cart cart31 = new Cart(guitare,user3,20);
        cart.save(cart31);

        assertEquals(cart.count(),6);

        List<Cart> listUser1 = cart.findAllItems2(user1.getId());
        assertEquals("userId: 1, articleId: 1 ,quantity: 2",listUser1.get(0).toString());
        assertEquals("userId: 1, articleId: 2 ,quantity: 3",listUser1.get(1).toString());
        assertEquals("userId: 1, articleId: 3 ,quantity: 5",listUser1.get(2).toString());


        List<Cart> listUser2 = cart.findAllItems2(user2.getId());
        assertEquals("userId: 2, articleId: 1 ,quantity: 20",listUser2.get(0).toString());
        assertEquals("userId: 2, articleId: 3 ,quantity: 10",listUser2.get(1).toString());

        List<Cart> listUser3 = cart.findAllItems2(user3.getId());
        assertEquals("userId: 3, articleId: 2 ,quantity: 20",listUser3.get(0).toString());
    }
}
