package com.lapetitesyrienne.api.models.response;

import java.util.Date;

import com.lapetitesyrienne.api.models.Commande;
import com.lapetitesyrienne.api.models.CommandeItem;
import com.lapetitesyrienne.api.models.UserDTO;

public class CommandeDTO {

    private String id;
    private String numero;
    private Date date;
    private String etat;
    private String type;
    private UserDTO client;
    private Double price;
    private CommandeItem[] items;

    public CommandeDTO(Commande cmd) {
        this.id = cmd.getId();
        this.numero = cmd.getNumero();
        this.date = cmd.getDate();
        this.type = cmd.getType();
        this.etat = cmd.getEtat();
        this.client = new UserDTO(cmd.getClient());
        this.price = cmd.getPrice();
        this.items = cmd.getItems();
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

    public UserDTO getClient() {
        return client;
    }

    public void setClient(UserDTO client) {
        this.client = client;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public void setItems(CommandeItem[] items) {
        this.items = items;
    }
}
