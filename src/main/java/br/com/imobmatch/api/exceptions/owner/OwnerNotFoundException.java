package br.com.imobmatch.api.exceptions.owner;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException() {
        super("The owner associated with the user does not exist");us

    }
}
