package com.amt.app.service;

import com.amt.app.entities.Article;
import com.amt.app.entities.Category;
import com.amt.app.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleRepository repository;

    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository){
        this.repository = articleRepository;
    }

    public List<Article> listAll(){
        return repository.findAll();
    }

    public Article get(int id){
        return repository.findById(id).get();
    }

    public Article addArticle(Article article) {
        return repository.save(article);
    }

    public void delete(Integer id){
        repository.deleteById(id);
    }

    public List<Article> getArticlesByCategoryId(int categoryId){
        List<Integer> articlesId = repository.getArticlesIdByCategoryId(categoryId);
        List<Article> listArticles = new ArrayList<>();
        for(Integer id : articlesId){
            listArticles.add(this.get(id));
        }
        return listArticles;
    }



}
