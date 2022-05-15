package com.lapetitesyrienne.api.controllers;

import com.lapetitesyrienne.api.models.Menu;
import com.lapetitesyrienne.api.models.Produit;
import com.lapetitesyrienne.api.models.request.MenuRequest;
import com.lapetitesyrienne.api.models.response.ResponseMessage;
import com.lapetitesyrienne.api.repository.MenuRepository;
import com.lapetitesyrienne.api.repository.ProduitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    ProduitRepository produitRepository;

    @GetMapping()
    public ResponseEntity<?> getMenu() {
        return ResponseEntity.status(HttpStatus.OK).body(menuRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenu(@PathVariable String id) {
        if (menuRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(menuRepository.findById(id).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Menu not found"));
    }

    @PostMapping()
    public ResponseEntity<?> createMenu(@RequestBody MenuRequest entity) {
        Produit[] produits = new Produit[entity.getProduits().length];
        for (int i = 0; i < entity.getProduits().length; i++) {
            if (!produitRepository.findById(entity.getProduits()[i]).isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("Produit " + entity.getProduits()[i] + " not found"));
            }
            produits[i] = produitRepository.findById(entity.getProduits()[i]).get();
        }
        Menu menu = new Menu(entity.getName(), entity.getDescription(), entity.getImage(), entity.getPrice(), produits);
        menuRepository.save(menu);
        return ResponseEntity.status(HttpStatus.CREATED).body(menu);
    }


}
