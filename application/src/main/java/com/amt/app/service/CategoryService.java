package com.amt.app.service;

import com.amt.app.entities.Article;
import com.amt.app.entities.Category;
import com.amt.app.repository.ArticleRepository;
import com.amt.app.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    public void setArticleRepository(CategoryRepository categoryRepository){
        this.repository = categoryRepository;
    }

    public List<Category> listAll(){
        return repository.findAll();
    }

    public Category get(int id){
        return repository.findById(id).get();
    }

    public Category addCategory(Category article) {
        return repository.save(article);
    }

    public void delete(int id){
        repository.deleteById(id);
    }

    public List<Category> getAllCategoriesLinkedToArticles(){
        List<Integer> categoriesId = repository.getAllCategoriesLinkedToArticles();
        List<Category> listCategories = new ArrayList<>();

        for(Integer id: categoriesId){
            listCategories.add(this.get(id));
        }
        return listCategories;
    }
}
