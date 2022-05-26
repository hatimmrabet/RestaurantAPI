package com.lapetitesyrienne.api.models.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.lapetitesyrienne.api.models.CommandeItem;
import com.lapetitesyrienne.api.models.User;

public class CommandeRequest {

    @NotEmpty @NotNull @NotEmpty
    private String type;
    private User client;
    private CommandeItem[] items;

    public CommandeRequest() {
    }

    public CommandeRequest(String type, User client, CommandeItem[] items) {
        this.type = type;
        this.client = client;
        this.items = items;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public CommandeItem[] getItems() {
        return items;
    }

    public void setItems(CommandeItem[] items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
