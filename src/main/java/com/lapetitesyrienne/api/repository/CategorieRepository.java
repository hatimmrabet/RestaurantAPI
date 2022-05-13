package com.lapetitesyrienne.api.repository;

import com.lapetitesyrienne.api.models.Categorie;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategorieRepository extends MongoRepository<Categorie, String> {

    Categorie findByName(String name);
    Boolean existsByName(String name);

}
