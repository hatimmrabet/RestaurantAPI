package com.lapetitesyrienne.api.models.request;

public class MenuRequest {

    String name; 
    String description;
    String image; 
    Double price;
    String[] produits;

    public MenuRequest() {
    }

    public MenuRequest(String name, String description, String image, Double price, String[] produits) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.produits = produits;
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

    public String[] getProduits() {
        return produits;
    }

    public void setProduits(String[] produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "MenuRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", produits=" + produits +
                '}';
    }
    
}
