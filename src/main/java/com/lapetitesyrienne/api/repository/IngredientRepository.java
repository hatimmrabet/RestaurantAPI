package com.lapetitesyrienne.api.repository;

import com.lapetitesyrienne.api.models.Ingredient;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {

    Ingredient findByName(String name);
    Boolean existsByName(String name);
    
    
}