package com.lapetitesyrienne.api.models;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION, defaultImpl = ArticleCommande.class)
@JsonSubTypes({ @Type(MenuCommande.class), @Type(ProduitCommande.class) })
public abstract class ArticleCommande {

    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String image;
    private Double price;
    private Date createdAt;

    public ArticleCommande() {
    }

    public ArticleCommande(String name, String description, String image, Double price) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.createdAt = new Date();
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Article [id=" + id + ", name=" + name + ", description=" + description + ", image=" + image + ", price="
                + price + "]";
    }

}