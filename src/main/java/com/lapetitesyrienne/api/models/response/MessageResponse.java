package com.lapetitesyrienne.api.models.response;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class MessageResponse {

    private int status;
    private String message;
    private String error;
    private String path;
    private String timestamp;

    public MessageResponse(String message) {
        this.message = message;
        this.timestamp = new Date().toString();
    }

    public MessageResponse(HttpStatus status, String message) {
        this.status = status.value();
        if(status.is4xxClientError()) {
            this.error = message;
        } else {
            this.message = message;
        }
        this.timestamp = new Date().toString();
    }

    public MessageResponse(HttpStatus status, String message, String path) {
        this.status = status.value();
        if (status.is4xxClientError()) {
            this.error = message;
        } else {
            this.message = message;
        }        this.path = path;
        this.timestamp = new Date().toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
