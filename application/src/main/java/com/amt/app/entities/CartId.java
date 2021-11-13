package com.amt.app.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CartId implements Serializable {

    @Column(name = "id", table = "user")
    private Integer userId;
    @Column(name = "id", table = "article")
    private Integer articleId;


    public CartId(){
    }

    public CartId(Integer userId, Integer articleId){
        this.userId = userId;
        this.articleId = articleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
}
