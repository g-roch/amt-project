package com.amt.app.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity //indique que c'est une identité JPA. Article est map à une table nommée 'Article'
@Table(name ="article")
public class Article {

    @Id //identifie le champ comme la clé primaire de l'objet
    @GeneratedValue(strategy = GenerationType.IDENTITY) //On définit qu'on génère les id en fonction de la stratégie mise dans mysql -> auto-increment
    private Integer id;
    private String name;
    private float price;
    private String description;
    private String image;
    private int stock;

    public Article() {
    }

    public Article(String name, float price, String description, String image, int stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
