package br.com.imobmatch.api.exceptions.property;

public class PropertyNotUploadImagen extends RuntimeException {
    public PropertyNotUploadImagen() {
        super("This property can no longer upload images.");
    }
}