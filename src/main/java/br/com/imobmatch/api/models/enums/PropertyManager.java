package br.com.imobmatch.api.models.enums;

import lombok.Getter;

@Getter
public enum PropertyManager {

    OWNER("owner"),
    ADDRESS("address");
    private String value;
    PropertyManager(String value) {}
}
