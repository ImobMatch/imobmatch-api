package br.com.imobmatch.api.models.enums;

public enum BrokerAccountStatus {
    ACTIVE("active"),
    BLOCKED("blocked"),
    PENDING("pending"),
    REJECTED("rejected");

    private final String value;

    BrokerAccountStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
