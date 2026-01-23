package br.com.imobmatch.api.models.enums;

public enum BrokerPropertyType {
    APARTMENT("apartment"),
    HOUSE("house"),
    LOFT("loft"),
    PENTHOUSE("penthouse"),
    STUDIO("studio");

    private final String value;

    BrokerPropertyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
