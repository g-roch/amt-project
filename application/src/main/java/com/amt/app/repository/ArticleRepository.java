package com.amt.app.repository;

import com.amt.app.entities.Article;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Qualifier("articles")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {




}
