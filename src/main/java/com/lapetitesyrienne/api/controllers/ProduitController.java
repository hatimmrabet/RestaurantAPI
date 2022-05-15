package com.lapetitesyrienne.api.controllers;

import com.lapetitesyrienne.api.models.Ingredient;
import com.lapetitesyrienne.api.models.Produit;
import com.lapetitesyrienne.api.models.response.ResponseMessage;
import com.lapetitesyrienne.api.repository.CategorieRepository;
import com.lapetitesyrienne.api.repository.IngredientRepository;
import com.lapetitesyrienne.api.repository.ProduitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/produits")
public class ProduitController {

    @Autowired
    ProduitRepository produitRepository;

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @GetMapping()
    public ResponseEntity<?> getProduit() {
        return ResponseEntity.status(HttpStatus.OK).body(produitRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduit(@PathVariable String id) {
        if(produitRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(produitRepository.findById(id).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Produit not found"));
    }

    @PostMapping()
    public ResponseEntity<?> createProduit(@RequestBody Produit entity) {
        entity.setCategorie(categorieRepository.findByName(entity.getCategorie().getName()));
        Ingredient[] ingredients = new Ingredient[entity.getIngredients().length];
        for (int i = 0; i < entity.getIngredients().length; i++) {
            // create ingredient if not exist
            if (ingredientRepository.findByName(entity.getIngredients()[i].getName()) == null) {
                ingredientRepository.save(entity.getIngredients()[i]);
            }
            ingredients[i] = ingredientRepository.findByName(entity.getIngredients()[i].getName());
        }
        entity.setIngredients(ingredients);
        produitRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduit(@PathVariable String id) {
        if(produitRepository.findById(id).isPresent()) {
            produitRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Produit deleted"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Produit not found"));
    }
    
}
