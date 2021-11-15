package com.amt.app.repository;

import com.amt.app.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

//L'interface nous permet de définir une classe spécifique à un domaine <Article> d'un certain type <int>
//Grâce au extends JpaRepository, on peut avoir accès aux methodes CRUD
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    //Le fait d'extend JpaRepository expose toutes les méthodes disponibles permettant de manipuler les entités.
    //Si on veut restreindre l'exposition des méthodes, il faut spécifier ici les méthodes que l'on veut utiliser.
    //https://docs.spring.io/spring-data/jpa/docs/2.6.0/reference/html/#appendix.query.method.subject
    //https://docs.spring.io/spring-data/jpa/docs/2.6.0/reference/html/#appendix.query.method.predicate
    //TODO: Voir si on utiliserait pas une interface de base pour toutes les classes spécifiques à un domaine. Cela permettrait d'exposer certaines methodes à toutes les classes, puis de spécifier dans chaque classes les méthodes utiles uniquement à elles-mêmes.
}
