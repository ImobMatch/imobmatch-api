package br.com.imobmatch.api.exceptions.auth;

public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException() {
        super("Token expired. Please login again");
    }
}
