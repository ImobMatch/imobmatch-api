package br.com.imobmatch.api.exceptions.user;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("User Not Found");
    }
}
