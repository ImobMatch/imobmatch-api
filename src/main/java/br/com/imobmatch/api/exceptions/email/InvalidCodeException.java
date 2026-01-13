package br.com.imobmatch.api.exceptions.email;

public class InvalidCodeException extends RuntimeException {
    public InvalidCodeException() {
        super("Invalid code");
    }
}
