package com.aironman.demo.es.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aironman.demo.es.model.Article;

public interface ArticleService {
    Article save(Article article);

    Article findOne(String id);

    Iterable<Article> findAll();

    Page<Article> findByAuthorName(String name, Pageable pageable);

    Page<Article> findByAuthorNameUsingCustomQuery(String name, Pageable pageable);

    Page<Article> findByFilteredTagQuery(String tag, Pageable pageable);

    Page<Article> findByAuthorsNameAndFilteredTagQuery(String name, String tag, Pageable pageable);

    long count();

    void delete(Article article);
}
