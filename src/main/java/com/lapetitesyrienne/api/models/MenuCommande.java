package com.lapetitesyrienne.api.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "menus")
public class MenuCommande extends ArticleCommande {

    private Produit[] produits;

    public MenuCommande() {
    }

    public MenuCommande(String name, String description, String image, Double price, Produit[] produits) {
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