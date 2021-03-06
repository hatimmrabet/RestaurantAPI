package com.lapetitesyrienne.api.repository;

import java.util.List;

import com.lapetitesyrienne.api.models.Menu;
import com.lapetitesyrienne.api.models.Produit;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MenuRepository extends MongoRepository<Menu, String> {

    Menu findByName(String name);
    Boolean existsByName(String name);
    List<Menu> findByOrderByCreatedAtDesc();
    List<Menu> findByProduitsContaining(Produit product);

}
