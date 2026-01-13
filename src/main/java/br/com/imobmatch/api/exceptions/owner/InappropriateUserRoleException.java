package br.com.imobmatch.api.exceptions.owner;

public class InappropriateUserRoleException extends RuntimeException {
    public InappropriateUserRoleException() {
        super("User role is not owner");
    }
}
