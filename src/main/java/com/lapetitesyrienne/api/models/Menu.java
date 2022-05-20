package com.lapetitesyrienne.api.models;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Menu {
    
    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String image;
    private Double price;
    @DBRef
    private Produit[] produits;

    public Menu() {
    }

    public Menu(String name, String description, String image, Double price, Produit[] produits) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.produits = produits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
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
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", produits=" + produits +
                '}';
    }

}
