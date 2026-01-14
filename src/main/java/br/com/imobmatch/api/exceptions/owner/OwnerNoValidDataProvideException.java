package br.com.imobmatch.api.exceptions.owner;

public class OwnerNoValidDataProvideException extends RuntimeException {
    public OwnerNoValidDataProvideException() {
        super("No invalid data was provided in the request");
    }
}
