package com.amt.app.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //indique que c'est une identité JPA. Cart est map à une table nommée 'Cart'

public class Cart {

    @Id //identifie le champ comme la clé primaire de l'objet
    private Integer userId;
    private Integer quantity;
    private Integer articleId;

    public Cart() {
    }

    public Integer getUserId() {
        return userId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public Integer getArticleId() {
        return articleId;
    }
}
