package com.lapetitesyrienne.api.models;

public class Menu extends Article {
    
    private Produit[] produits;

    public Menu(String name, String description, String image, Double price, Produit[] produits) {
        super(name, description, image, price);
        this.produits = produits;
    }

    public Menu() {
    }

    public Produit[] getProduits() {
        return produits;
    }

    public void setProduits(Produit[] produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "produits=" + produits +
                '}';
    }

}
