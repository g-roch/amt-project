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
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class CartTest {

    //@Autowired private EntityManager entityManager;
    @Autowired private CartRepository cart;
    @Autowired private UserRepository user;
    @Autowired private CategoryRepository category;
    @Autowired private ArticleRepository article;

    @Test
    public void test(){
        Article piano = new Article("Piano2", 520F, "Piano a queue","f1.png", 5);
        Article guitare = new Article("Guitarre2", 80F, "Gibson","f1.png", 10);
        Article flute = new Article("Flute2",140F , "Flute traversiere","f1.png", 15);
        article.save(piano);
        article.save(guitare);
        article.save(flute);

        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setUsername("nico");
        user2.setUsername("gab");
        user3.setUsername("chris");
        user.save(user1);
        user.save(user2);
        user.save(user3);

        Category flutes = new Category();
        Category vents = new Category();
        Category pianos = new Category();
        Category guitares = new Category();
        Category cordes = new Category();
        Category percussions = new Category();
        flutes.setName("flutes");
        vents.setName("instrumentsAVent");
        pianos.setName("pianos");
        guitares.setName("guitares");
        cordes.setName("cordes");
        percussions.setName("percussions");

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

        List<Cart> listUser1 = cart.findCartsByUserIdCustom(user1.getId());
        assertEquals("userId: 1, articleId: 1 ,quantity: 2",listUser1.get(0).toString());
        assertEquals("userId: 1, articleId: 2 ,quantity: 3",listUser1.get(1).toString());
        assertEquals("userId: 1, articleId: 3 ,quantity: 5",listUser1.get(2).toString());


        List<Cart> listUser2 = cart.findCartsByUserIdCustom(user2.getId());
        assertEquals("userId: 2, articleId: 1 ,quantity: 20",listUser2.get(0).toString());
        assertEquals("userId: 2, articleId: 3 ,quantity: 10",listUser2.get(1).toString());

        List<Cart> listUser3 = cart.findCartsByUserIdCustom(user3.getId());
        assertEquals("userId: 3, articleId: 2 ,quantity: 20",listUser3.get(0).toString());
    }
}