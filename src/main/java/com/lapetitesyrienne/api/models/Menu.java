package com.lapetitesyrienne.api.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "menus")
public class Menu extends Article {
    
    @DBRef
    private Produit[] produits;

    public Menu() {
    }

    public Menu(String name, String description, String image, float price, Produit[] produits) {
        super(name, description, image, price);
        this.produits = produits;
    }

    public Produit[] getProduits() {
        return produits;
    }

    public void setProduits(Produit[] produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Menu [produits=" + produits + "]";
    }

}
