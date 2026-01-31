package br.com.imobmatch.api.exceptions.email;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException() {
        super("Request Not Found");
    }
}
