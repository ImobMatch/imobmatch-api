package br.com.imobmatch.api.exceptions.broker;

public class BrokerExistsException extends RuntimeException {
    public BrokerExistsException() {
        super("The cpf informed already exists");
    }
    
}
