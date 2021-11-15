package com.amt.app.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity //indique que c'est une identité JPA. Article est map à une table nommée 'Article'
@Table(name = "article")
public class Article {

    @Id //identifie le champ comme la clé primaire de l'objet
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //On définit qu'on génère les id en fonction de la stratégie mise dans mysql -> auto-increment
    private int id;

    @NotEmpty(message = "Article's name cannot be empty.")
    private String name;

    private float price;

    @NotEmpty(message = "Article's description cannot be empty.")
    private String description;
    private String image;

    @Min(1)
    private int stock;

    public Article() {
    }

    @Id
    public int getId() {
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

    public void setId(int id) {
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
