package br.com.imobmatch.api.exceptions.broker;

public class BrokerNotFoundException extends RuntimeException {
    public BrokerNotFoundException() {
        super("The broker associated with the user does not exist");
    }
    
}
