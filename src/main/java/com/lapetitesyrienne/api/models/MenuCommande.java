package com.lapetitesyrienne.api.models;

public class MenuCommande extends ArticleCommande {

    private ProduitCommande[] produits;

    public MenuCommande() {
    }

    public MenuCommande(String name, String description, String image, Double price, ProduitCommande[] produits) {
        super(name, description, image, price);
        this.produits = produits;
    }

    public ProduitCommande[] getProduits() {
        return produits;
    }

    public void setProduits(ProduitCommande[] produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Menu [produits=" + produits + "]";
    }

}