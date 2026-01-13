package br.com.imobmatch.api.exceptions.broker;

public class BrokerNoValidDataProvideException extends RuntimeException {
    public BrokerNoValidDataProvideException() {
        super("No valid data was provided in the request");
    }
    
}
