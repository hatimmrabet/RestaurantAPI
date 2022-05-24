package com.lapetitesyrienne.api.models.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.lapetitesyrienne.api.models.CommandeItem;

public class CommandeRequest {

    @NotEmpty @NotNull @NotEmpty
    private String type;
    private String clientID;
    private CommandeItem[] items;

    public CommandeRequest() {
    }

    public CommandeRequest(String type, String clientID, CommandeItem[] items) {
        this.type = type;
        this.clientID = clientID;
        this.items = items;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
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
