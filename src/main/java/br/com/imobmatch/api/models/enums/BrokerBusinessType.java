package br.com.imobmatch.api.models.enums;

public enum BrokerBusinessType {
    SALE("sale"),
    RENT("rent"),
    SALE_AND_RENT("saleAndRent");

    private final String value;

    BrokerBusinessType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
}
