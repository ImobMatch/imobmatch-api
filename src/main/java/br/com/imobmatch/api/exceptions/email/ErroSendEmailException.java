package br.com.imobmatch.api.exceptions.email;

public class ErroSendEmailException extends RuntimeException {
    public ErroSendEmailException(String message) {
        super(message);
    }
}
