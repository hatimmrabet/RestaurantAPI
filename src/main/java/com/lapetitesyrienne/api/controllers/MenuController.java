package com.lapetitesyrienne.api.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import com.lapetitesyrienne.api.models.Menu;
import com.lapetitesyrienne.api.models.Produit;
import com.lapetitesyrienne.api.models.response.ResponseMessage;
import com.lapetitesyrienne.api.repository.MenuRepository;
import com.lapetitesyrienne.api.repository.ProduitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/menus")
public class MenuController {

    File root = new File("src/main/resources/static/images");

    @Autowired
    MenuRepository menuRepository;
    @Autowired
    ProduitRepository produitRepository;

    @GetMapping()
    public ResponseEntity<?> getMenu() {
        return ResponseEntity.status(HttpStatus.OK).body(menuRepository.findByOrderByCreatedAtDesc());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenu(@PathVariable String id) {
        if (menuRepository.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(menuRepository.findById(id).get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Menu not found"));
    }

    @PostMapping()
    public ResponseEntity<?> createMenu(@RequestParam("image") MultipartFile image,
            @RequestParam("name") String name, @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("produits") String[] produits_id) {
        Menu menu = new Menu();
        menu.setName(name);
        menu.setPrice(price);
        menu.setDescription(description);
        menu.setCreatedAt(new Date());
        Produit[] produits = new Produit[produits_id.length];
        for (int i = 0; i < produits_id.length; i++) {
            if (!produitRepository.findById(produits_id[i]).isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("Produit " + produits_id[i] + " not found"));
            }
            produits[i] = produitRepository.findById(produits_id[i]).get();
        }
        menu.setProduits(produits);
        // enregistrer menu pour avoir l'id pour l'image
        menuRepository.save(menu);
        // nom de l'image
        menu.setImage(menu.getId() + image.getOriginalFilename());
        // upload image
        try {
            File path = new File(root.getAbsolutePath() + "/" + menu.getImage());
            path.createNewFile();
            FileOutputStream output = new FileOutputStream(path);
            output.write(image.getBytes());
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // enregistrer menu
        menuRepository.save(menu);
        return ResponseEntity.status(HttpStatus.CREATED).body(menu);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> editMenu(@PathVariable String id, 
            @RequestParam(name="image", required = false) MultipartFile image,
            @RequestParam("name") String name, @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("produits") String[] produits_id) {

        if (menuRepository.findById(id).isPresent()) {
            Menu menu = menuRepository.findById(id).get();
            menu.setName(name);
            menu.setPrice(price);
            menu.setDescription(description);
            menu.setCreatedAt(menu.getCreatedAt());
            // get products by ids
            Produit[] produits = new Produit[produits_id.length];
            for (int i = 0; i < produits_id.length; i++) {
                if (!produitRepository.findById(produits_id[i]).isPresent()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ResponseMessage("Produit " + produits_id[i] + " not found"));
                }
                produits[i] = produitRepository.findById(produits_id[i]).get();
            }
            menu.setProduits(produits);
            if(image != null) {
                // supprimer l'ancienne image
                File oldImage = new File(root.getAbsolutePath() + "/" + menu.getImage());
                oldImage.delete();
                // enregistrer la nouvelle image
                menu.setImage(menu.getId() + image.getOriginalFilename());
                try {
                    File path = new File(root.getAbsolutePath() + "/" + menu.getImage());
                    path.createNewFile();
                    FileOutputStream output = new FileOutputStream(path);
                    output.write(image.getBytes());
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // enregistrer menu
            menuRepository.save(menu);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Menu updated"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Menu not found"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable String id) {
        if (menuRepository.findById(id).isPresent()) {
            Menu menu = menuRepository.findById(id).get();
            // supprimer l'image
            File image = new File(root.getAbsolutePath() + "/" + menu.getImage());
            image.delete();
            // supprimer le menu
            menuRepository.delete(menu);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Menu deleted"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Menu not found"));
    }

}
