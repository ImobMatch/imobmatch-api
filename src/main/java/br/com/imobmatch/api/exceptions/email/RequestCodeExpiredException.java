package br.com.imobmatch.api.exceptions.email;

public class RequestCodeExpiredException extends RuntimeException {
    public RequestCodeExpiredException() {
        super("Request code expired");
    }
}
