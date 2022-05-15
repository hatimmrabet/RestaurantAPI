package com.lapetitesyrienne.api.repository;


import com.lapetitesyrienne.api.models.Article;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface ArticleRepository extends MongoRepository<Article, String> {

    Article findByName(String name);
    Boolean existsByName(String name);
    
}
