package com.amt.app.service;

import com.amt.app.entities.Article;
import com.amt.app.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleRepository repository;

    public List<Article> listAll(){
        return repository.findAll();
    }

    public Article get(Integer id){
        return repository.findById(id).get();
    }

    public void save(Article article){
        repository.save(article);
    }

    public void delete(Integer id){
        repository.deleteById(id);
    }




}
