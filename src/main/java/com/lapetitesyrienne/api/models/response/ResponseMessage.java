package com.lapetitesyrienne.api.models.response;

public class ResponseMessage {

    private String response;

    public ResponseMessage(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


}