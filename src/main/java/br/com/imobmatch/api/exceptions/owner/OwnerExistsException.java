package br.com.imobmatch.api.exceptions.owner;

public class OwnerExistsException extends RuntimeException {
    public OwnerExistsException() {
        super("The cpf informed already exists");
    }
}
