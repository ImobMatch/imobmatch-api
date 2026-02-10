package br.com.imobmatch.api.models.enums;

public enum PropertyType {

    APARTMENT("apartment"),
    HOUSE("house"),
    COMMERCIAL("commercial"),
    LOFT("loft"),
    PENTHOUSE("penthouse"),
    STUDIO("studio");

    private final String value;
    PropertyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
