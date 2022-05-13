package com.lapetitesyrienne.api.controllers;

import javax.validation.Valid;

import com.lapetitesyrienne.api.exceptions.IngredientNotFoundException;
import com.lapetitesyrienne.api.models.Ingredient;
import com.lapetitesyrienne.api.models.response.ResponseMessage;
import com.lapetitesyrienne.api.repository.IngredientRepository;

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

    @PostMapping()
    public ResponseEntity<?> postMethodName(@Valid @RequestBody Ingredient entity) {
        if(ingredientRepository.findByName(entity.getName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage("Ingredient already exists"));
        }
        ingredientRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Ingredient created"));
    }

    @GetMapping()
    public ResponseEntity<?> getMethodName() {
        return ResponseEntity.status(HttpStatus.OK).body(ingredientRepository.findAll());
    }

    @PutMapping("{id}")
    public ResponseEntity<?> putMethodName(@PathVariable String id, @RequestBody Ingredient entity) {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new IngredientNotFoundException(entity.getName()));
        ingredient.setName(entity.getName());
        ingredientRepository.save(ingredient);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Ingredient updated"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMethodName(@PathVariable String id) {
        ingredientRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Ingredient deleted"));
    }
    
    
}