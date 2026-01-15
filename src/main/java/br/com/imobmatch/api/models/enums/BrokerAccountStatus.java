package br.com.imobmatch.api.models.enums;

public enum BrokerAccountStatus {
    VALID("valid"),
    INVALID("invalid"),
    PENDING("pending");

    private final String value;

    BrokerAccountStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
