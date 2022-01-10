/**
 * Entity that represents a CartId because Cart doesn't have a uniq id
 * @see Cart.java
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gabriel Roch, Christian Zaccaria
 */

package com.amt.app.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

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