package br.com.imobmatch.api.exceptions;

public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException() {
        super("Property not found");
    }
}
