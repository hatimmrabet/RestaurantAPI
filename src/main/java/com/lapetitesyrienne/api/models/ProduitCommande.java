package com.lapetitesyrienne.api.models;

public class ProduitCommande extends ArticleCommande {

    private Categorie categorie;
    private Ingredient[] ingredients;

    public ProduitCommande() {
    }

    public ProduitCommande(String name, String description, String image, float price, Categorie categorie, Ingredient[] ingredients) {
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
