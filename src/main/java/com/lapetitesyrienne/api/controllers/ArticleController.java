package com.lapetitesyrienne.api.controllers;

import com.lapetitesyrienne.api.models.Ingredient;
import com.lapetitesyrienne.api.models.Menu;
import com.lapetitesyrienne.api.models.Produit;
import com.lapetitesyrienne.api.models.request.MenuRequest;
import com.lapetitesyrienne.api.models.response.ResponseMessage;
import com.lapetitesyrienne.api.repository.ArticleRepository;
import com.lapetitesyrienne.api.repository.CategorieRepository;
import com.lapetitesyrienne.api.repository.IngredientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @GetMapping()
    public ResponseEntity<?> getArticle() {
        return ResponseEntity.status(HttpStatus.OK).body(articleRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticle(@PathVariable String id) {
        if(articleRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(articleRepository.findById(id).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Article not found"));
    }

    @PostMapping("/produit")
    public ResponseEntity<?> createProduit(@RequestBody Produit entity) {
        entity.setCategorie(categorieRepository.findByName(entity.getCategorie().getName()));
        Ingredient[] ingredients = new Ingredient[entity.getIngredients().length];
        for(int i = 0; i < entity.getIngredients().length; i++) {
            // create ingredient if not exist
            if(ingredientRepository.findByName(entity.getIngredients()[i].getName()) == null) {
                ingredientRepository.save(entity.getIngredients()[i]);
            }
            ingredients[i] = ingredientRepository.findByName(entity.getIngredients()[i].getName());
        }
        entity.setIngredients(ingredients);
        articleRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PostMapping("/menu")
    public ResponseEntity<?> createMenu(@RequestBody MenuRequest entity) {
        Produit[] produits = new Produit[entity.getProduits().length];
        for(int i = 0; i < entity.getProduits().length; i++) {
            if(!articleRepository.findById(entity.getProduits()[i]).isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Produit "+entity.getProduits()[i]+" not found"));
            }
            produits[i] = (Produit) articleRepository.findById(entity.getProduits()[i]).get();
        }
        Menu menu = new Menu(entity.getName(), entity.getDescription(), entity.getImage(), entity.getPrice(), produits);
        articleRepository.save(menu);
        return ResponseEntity.status(HttpStatus.CREATED).body(menu);
    }
    


    
    
}
