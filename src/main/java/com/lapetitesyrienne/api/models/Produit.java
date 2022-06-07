package com.lapetitesyrienne.api.models;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "produits")
public class Produit extends Article {

    @DBRef
    private Categorie categorie;
    @DBRef
    private Ingredient[] ingredients;

    public Produit() {
    }

    public Produit(String name, String description, String image, float price, Categorie categorie, Ingredient[] ingredients) {
        super(name, description, image, price);
        this.categorie = categorie;
        this.ingredients = ingredients;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Produit [categorie=" + categorie + ", ingredients=" + ingredients + "]";
    }

}
