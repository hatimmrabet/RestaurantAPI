package com.lapetitesyrienne.api.controllers;

import java.util.List;

import javax.validation.Valid;

import com.lapetitesyrienne.api.exceptions.IngredientNotFoundException;
import com.lapetitesyrienne.api.models.Ingredient;
import com.lapetitesyrienne.api.models.Produit;
import com.lapetitesyrienne.api.models.response.ResponseMessage;
import com.lapetitesyrienne.api.repository.IngredientRepository;
import com.lapetitesyrienne.api.repository.ProduitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    IngredientRepository ingredientRepository;
    @Autowired
    ProduitRepository produitRepository;

    @PostMapping()
    public ResponseEntity<?> createIngredient(@Valid @RequestBody Ingredient entity) {
        if(ingredientRepository.findByNameIgnoreCase(entity.getName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage("Ingredient already exists"));
        }
        // change the name of the ingredient
        entity.formatName();
        ingredientRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @GetMapping()
    public ResponseEntity<?> getIngredients() {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientRepository.findByOrderByNameAsc());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateIngredient(@PathVariable String id, @RequestBody Ingredient entity) {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new IngredientNotFoundException(entity.getName()));
        entity.formatName();
        ingredient.setName(entity.getName());
        ingredientRepository.save(ingredient);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Ingredient updated"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable String id) {
        if(!ingredientRepository.findById(id).isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Ingredient not found"));

        Ingredient ingredient = ingredientRepository.findById(id).get();
        List<Produit> produits = produitRepository.findByIngredientsContaining(ingredient);
        if(produits.size()>0)
        {
            String ret = "";
            for(Produit p : produits)
            {
                ret += p.getName() + ", ";
            }
            if (!ret.equals("")) {
                ret = ret.substring(0, ret.length() - 2)+".";
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage("Ingredient cannot be deleted, it is used by : " + ret));
        } else {
            ingredientRepository.delete(ingredient);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Ingredient deleted"));
        }
    }
    
    
}
