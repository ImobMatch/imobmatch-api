package br.com.imobmatch.api.exceptions.property;

public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException() {
        super("Property not found");
    }
}
