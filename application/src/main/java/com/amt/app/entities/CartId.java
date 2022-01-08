package com.amt.app.entities;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

//Cette classe permet de représenter un id dans la classe Cart qui n'a pas d'id unique
//@Embeddable doit implémenter Serializable et override les méthodes equals et hashCode //TODO NGY - purpose of this comment ?
@Embeddable
public class CartId implements Serializable {

    @Column(name = "userid")
    private int userId;
    @Column(name = "articleid")
    private int articleId;


    public CartId(){
    }

    public CartId(int userId, int articleId){
        this.userId = userId;
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }

        CartId that = (CartId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(articleId, that.articleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, articleId);
    }

}