package com.lapetitesyrienne.api.exceptions;

public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String id) {
        super("Could not find user " + id);
    }
}
