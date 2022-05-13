package com.lapetitesyrienne.api.exceptions;

public class IngredientNotFoundException extends RuntimeException {
    
    public IngredientNotFoundException(String name) {
        super("Could not find ingredient with name : " + name);
    }

}
