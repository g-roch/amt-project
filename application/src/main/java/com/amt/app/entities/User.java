package com.amt.app.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity(name = "User")
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotEmpty(message = "username cannot be empty.")
    @Column(name = "username", unique = true)
    @Getter
    @Setter
    private String username;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Cart> articles = new ArrayList<>();

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    /*
    addArticle et removeArticle sont nécessaires car cela permet la synchronisation entre les deux cotés de l'association bidirectionnelle représentée par Cart
    On pourrait ajouter les mêmes méthodes à la classe Article, mais comme se sont les users qui vont ajouter des articles
    et pas inversement, ce n'est pas nécessaire.
     */
    public void addArticle(Article article, int quantity) {
        Cart cart = new Cart(article, this, quantity);
        articles.add(cart);
        article.getUsers().add(cart);
    }

    public void removeArticle(Article article) {
        for (Iterator<Cart> iterator = articles.iterator(); iterator.hasNext(); ) {
            Cart cart = iterator.next();
            if (cart.getUser().equals(this) && cart.getArticle().equals(article)) {
                iterator.remove();
                cart.getArticle().getUsers().remove(cart);
                cart.setArticle(null);
                cart.setUser(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}