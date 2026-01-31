package br.com.imobmatch.api.exceptions.auth;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Authentication Failed");
    }
}
