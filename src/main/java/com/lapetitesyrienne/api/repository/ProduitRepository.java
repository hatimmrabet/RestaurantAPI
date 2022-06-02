package com.lapetitesyrienne.api.repository;

import java.util.List;

import com.lapetitesyrienne.api.models.Categorie;
import com.lapetitesyrienne.api.models.Ingredient;
import com.lapetitesyrienne.api.models.Produit;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProduitRepository extends MongoRepository<Produit, String> {

    Produit findByName(String name);
    Boolean existsByName(String name);
    List<Produit> findByCategorie(Categorie categorie);
    List<Produit> findByCategorieName(String categorieName);
    List<Produit> findByOrderByCreatedAtDesc();
    List<Produit> findByIngredientsContaining(Ingredient ingredient);

}
