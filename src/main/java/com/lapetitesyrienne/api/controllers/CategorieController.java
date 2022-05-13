package com.lapetitesyrienne.api.controllers;

import javax.validation.Valid;

import com.lapetitesyrienne.api.models.Categorie;
import com.lapetitesyrienne.api.models.response.ResponseMessage;
import com.lapetitesyrienne.api.repository.CategorieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/categories")
public class CategorieController {

    @Autowired
    CategorieRepository categorieRepository;

    @PostMapping()
    public ResponseEntity<?> createCategorie(@Valid @RequestBody Categorie entity) {
        if(categorieRepository.findByName(entity.getName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage("Categorie already exists"));
        }
        categorieRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Categorie created"));
    }

    @GetMapping()
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categorieRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCategorie(@PathVariable String id) {
        if(categorieRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(categorieRepository.findById(id).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Categorie not found"));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCategorie(@PathVariable String id, @RequestBody Categorie entity) {
        if(categorieRepository.findById(id).isPresent()) {
            Categorie categorie = categorieRepository.findById(id).get();
            categorie.setName(entity.getName());
            categorieRepository.save(categorie);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Categorie updated"));
        }
        categorieRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("Categorie created"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategorie(@PathVariable String id) {
        if(categorieRepository.findById(id).isPresent()) {
            categorieRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Categorie deleted"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Categorie not found"));
    }
    
}
