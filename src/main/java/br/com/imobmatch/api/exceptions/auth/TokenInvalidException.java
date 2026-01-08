package br.com.imobmatch.api.exceptions.auth;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException() {
        super("Invalid token");
    }

    public TokenInvalidException(String message) {
        super(message);
    }
}
