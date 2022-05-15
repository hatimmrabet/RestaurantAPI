package com.lapetitesyrienne.api.models;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
public class Categorie {
    @Id
    private String id;
    @NotBlank
    private String name;

    public Categorie() {
    }

    public Categorie(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Categorie{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
