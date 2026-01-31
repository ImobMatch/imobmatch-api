package br.com.imobmatch.api.exceptions.auth;

public class CreateTokenException extends RuntimeException  {
    public CreateTokenException() {
        super("Erro Create Token");
    }
}
