package br.com.imobmatch.api.exceptions.user;

public class UserExists extends RuntimeException {
    public UserExists() {
        super("User with email already exists");
    }
}
