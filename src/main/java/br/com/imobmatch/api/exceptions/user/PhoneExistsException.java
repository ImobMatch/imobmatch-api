package br.com.imobmatch.api.exceptions.user;

public class PhoneExistsException extends RuntimeException {
    public PhoneExistsException() {
        super("Phone already exists");
    }
}
