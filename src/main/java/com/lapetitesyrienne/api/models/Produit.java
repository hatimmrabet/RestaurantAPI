package com.lapetitesyrienne.api.models;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Produit extends Article {

    @DBRef
    private Categorie categorie;
    @DBRef
    private Ingredient[] ingredients;

    public Produit(String name, String description, String image, Double price, Categorie categorie, Ingredient[] ingredients) {
        super(name, description, image, price);
        this.categorie = categorie;
        this.ingredients = ingredients;
    }

    public Produit() {
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "ingredients=" + ingredients +
                '}';
    }
}
