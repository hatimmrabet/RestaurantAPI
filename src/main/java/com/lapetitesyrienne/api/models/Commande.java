package com.lapetitesyrienne.api.models;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "commandes")
public class Commande {

    @Id
    private String id;
    private String numero;
    private Date date;
    private String etat;
    @NotBlank
    private String type;
    @DBRef
    private User client;
    private float price;
    private CommandeItem[] items;

    public Commande() {
    }

    public Commande(String numero, Date date, String etat, String type, User client, CommandeItem[] items) {
        this.numero = numero;
        this.date = date;
        this.etat = etat;
        this.type = type;
        this.client = client;
        this.price = 0.0f;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CommandeItem[] getItems() {
        return items;
    }

    public float countTotalPrice() {
        float total = 0;
        for (CommandeItem item : items) {
            total += item.getArticle().getPrice() * item.getQuantity();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Commande [id=" + id + ", numero=" + numero + ", date=" + date + ", etat=" + etat + ", type=" + type + ", client=" + client + ", price=" + price + ", items=" + items + "]";
    }

}
