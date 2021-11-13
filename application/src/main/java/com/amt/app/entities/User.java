package com.amt.app.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Integer id;

    public User() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}
