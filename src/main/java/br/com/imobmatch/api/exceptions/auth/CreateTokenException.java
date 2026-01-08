package br.com.imobmatch.api.exceptions.auth;

public class CreateTokenException extends RuntimeException  {
    public CreateTokenException(String message) {
        super(message);
    }
}
