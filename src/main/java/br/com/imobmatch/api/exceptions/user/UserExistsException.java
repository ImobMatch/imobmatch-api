package br.com.imobmatch.api.exceptions.user;

public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super("User with email already exists");
    }
}
