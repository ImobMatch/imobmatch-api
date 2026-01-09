package br.com.imobmatch.api.exceptions.owner;

public class OwnerNotExistsException extends RuntimeException {
    public OwnerNotExistsException() {
        super("The owner associated with the user does not exist");
    }
}
