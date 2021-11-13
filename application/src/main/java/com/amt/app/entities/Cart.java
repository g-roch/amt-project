package com.amt.app.entities;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    @EmbeddedId
    private CartId id;
    private int quantity;

    public Cart(){
    }

    public Cart(CartId id, int quantity){
        this.id = id;
        this.quantity = quantity;
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
}
