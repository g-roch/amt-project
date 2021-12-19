package com.amt.app.entities;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.Cache;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity (name="Article") //indique que c'est une identité JPA. Article est map à une table nommée 'Article'
@Table(name ="article")
@NaturalIdCache
@Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public class Article {

    @Id //identifie le champ comme la clé primaire de l'objet
    @GeneratedValue(strategy = GenerationType.IDENTITY) //On définit qu'on génère les id en fonction de la stratégie mise dans mysql -> auto-increment
    private int id;

    @NaturalId
    @NotEmpty(message = "Article's name cannot be empty.")
    @Column(nullable = false, unique = true)
    private String name;

    @Min(0)
    private Float price;

    @NotEmpty(message = "Article's description cannot be empty.")
    private String description;

    private String image;

    @Min(0)
    private int stock;

    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Cart> users = new ArrayList<>();

    @ManyToMany(mappedBy = "articles")
    private List<Category> categories = new ArrayList<>();

    public Article() {
    }

    public Article(String name, Float price, String description, String image, int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.stock = stock;
    }


    @Transient
    public String getPhotosImagePath() {
        if (image == null) return null;

        return "/article-photos/" + id + "/" + image;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Cart> getUsers() {
        return users;
    }

    public void setUsers(List<Cart> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Float getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(name, article.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}