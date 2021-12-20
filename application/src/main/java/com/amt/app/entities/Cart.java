package com.amt.app.entities;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "Cart")
@Table(name = "cart")
public class Cart {

    @EmbeddedId
    private CartId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("articleId")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @Column(name = "quantity")
    private int quantity;

    public Cart(){
    }

    public Cart(Article article, User user, int quantity){
        this.article = article;
        this.user = user;
        this.id = new CartId(user.getId(), article.getId());
        this.quantity = quantity;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CartId getId() {
        return id;
    }

    public void setId(CartId id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "userId: " + id.getUserId() + ", articleId: " + id.getArticleId() + " ,quantity: " + quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Cart that = (Cart) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(article, that.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, article);
    }
}