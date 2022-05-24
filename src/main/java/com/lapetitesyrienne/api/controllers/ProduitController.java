package com.lapetitesyrienne.api.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

import com.lapetitesyrienne.api.models.Categorie;
import com.lapetitesyrienne.api.models.Ingredient;
import com.lapetitesyrienne.api.models.Produit;
import com.lapetitesyrienne.api.models.response.ResponseMessage;
import com.lapetitesyrienne.api.repository.CategorieRepository;
import com.lapetitesyrienne.api.repository.IngredientRepository;
import com.lapetitesyrienne.api.repository.ProduitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/produits")
public class ProduitController {

    // prepare root folder
    File root = new File("src/main/resources/static/images");

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
        if (produitRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(produitRepository.findById(id).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Produit not found"));
    }

    @PostMapping()
    public ResponseEntity<?> createProduit(@RequestParam("image") MultipartFile image,
            @RequestParam("name") String name, @RequestParam("description") String description,
            @RequestParam("price") double price, @RequestParam("categorie") String categorieName,
            @RequestParam("ingredients") String[] ingredientsName) {
        // create product
        Produit produit = new Produit();
        produit.setName(name);
        produit.setDescription(description);
        produit.setPrice(price);
        // list des ingredients
        Ingredient[] ingredients = new Ingredient[ingredientsName.length];
        for (int i = 0; i < ingredientsName.length; i++) {
            if (ingredientRepository.findByNameIgnoreCase(ingredientsName[i]) == null) {
                Ingredient ingr = new Ingredient(ingredientsName[i]);
                ingr.formatName();
                ingredientRepository.save(ingr);
            }
            ingredients[i] = ingredientRepository.findByNameIgnoreCase(ingredientsName[i]);
        }
        produit.setIngredients(ingredients);
        // get categorie
        if (categorieRepository.findByNameIgnoreCase(categorieName) == null) {
            Categorie cat = new Categorie(categorieName);
            cat.formatName();
            categorieRepository.save(cat);
        }
        produit.setCategorie(categorieRepository.findByNameIgnoreCase(categorieName));
        // enregistrer le produit
        produitRepository.save(produit);
        // nom de l'image
        produit.setImage(produit.getId() + image.getOriginalFilename());
        // upload image
        try {
            File path = new File(root.getAbsolutePath() + "/" + produit.getImage());
            path.createNewFile();
            FileOutputStream output = new FileOutputStream(path);
            output.write(image.getBytes());
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // enregister le produit avec nom de l'image
        produitRepository.save(produit);
        return ResponseEntity.status(HttpStatus.CREATED).body(produit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduit(@PathVariable String id) {
        if (produitRepository.findById(id).isPresent()) {
            //delete image
            File image = new File(root.getAbsolutePath() + "/" + produitRepository.findById(id).get().getImage());
            image.delete();
            produitRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Produit deleted"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Produit not found"));
    }

    @GetMapping("/group-by-categories")
    public ResponseEntity<?> getProduitGroupByCategorie() {
        HashMap<String, List<Produit>> map = new HashMap<>();
        List<Categorie> categories = categorieRepository.findAll();
        for(Categorie categorie : categories) {
            map.put(categorie.getName(), produitRepository.findByCategorie(categorie));
        }
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
}
