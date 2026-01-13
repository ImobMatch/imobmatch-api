package br.com.imobmatch.api.models.broker.enums;

public enum BrokerBusinessType {
    SALE("sale"),
    RENTAL("rental"),
    LEASE("lease");

    private final String value;

    BrokerBusinessType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
}
