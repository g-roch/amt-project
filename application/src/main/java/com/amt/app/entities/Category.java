package com.amt.app.entities;

import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Validated
public class Category {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", unique = true)
    @NotEmpty(message = "Catégorie ne peut pas être vide.")
    private String name;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "article_category",
            joinColumns = @JoinColumn(name = "categoryid"),
            inverseJoinColumns = @JoinColumn(name = "articleid")
    )
    private List<Article> articles = new ArrayList<>();

    public void addOneArticle(Article article){
        this.articles.add(article);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
