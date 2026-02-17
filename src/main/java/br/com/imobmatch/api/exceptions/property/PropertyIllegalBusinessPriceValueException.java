package br.com.imobmatch.api.exceptions.property;

public class PropertyIllegalBusinessPriceValueException extends RuntimeException {
    public PropertyIllegalBusinessPriceValueException() {
        super("The price value associated in business type is invalid");
    }
}
