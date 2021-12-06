package com.amt.app.entities;

import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.Empty;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity //indique que c'est une identité JPA. Article est map à une table nommée 'User'
@Table(name = "user")
@Validated
public class User {

    @Id //identifie le champ comme la clé primaire de l'objet
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //On définit qu'on génère les id en fonction de la stratégie mise dans mysql -> auto-increment
    @Getter
    @Setter
    private int id;

    @NotEmpty(message = "username cannot be empty.")
    @Column(name="username", unique = true)
    @Getter
    @Setter
    private String username;

    public User(){

    }
}
