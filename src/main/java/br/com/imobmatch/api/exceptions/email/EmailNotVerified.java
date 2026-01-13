package br.com.imobmatch.api.exceptions.email;

public class EmailNotVerified extends RuntimeException {
    public EmailNotVerified() {
        super("Email not verified");
    }
}
