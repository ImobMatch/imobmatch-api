package br.com.imobmatch.api.exceptions.owner;

public class NoValidDataProvideException extends RuntimeException {
    public NoValidDataProvideException() {
        super("No invalid data was provided in the request");
    }
}
