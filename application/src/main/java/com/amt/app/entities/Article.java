package com.amt.app.entities;

import net.bytebuddy.implementation.bind.annotation.Empty;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity //indique que c'est une identité JPA. Article est map à une table nommée 'Article'
@Table(name = "article")
@Validated
public class Article {

    @Id //identifie le champ comme la clé primaire de l'objet
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //On définit qu'on génère les id en fonction de la stratégie mise dans mysql -> auto-increment
    private int id;

    @NotEmpty(message = "Article's name cannot be empty.")
    @Column(name="name", unique = true)
    private String name;

    @Min(0) 
    private Float price;

    @NotEmpty(message = "Article's description cannot be empty.")
    private String description;

    private String image;

    @Min(0)
    private int stock;

    @ManyToMany(mappedBy = "articles")
    private List<Category> categories = new ArrayList<>();

    public Article() {
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
}
