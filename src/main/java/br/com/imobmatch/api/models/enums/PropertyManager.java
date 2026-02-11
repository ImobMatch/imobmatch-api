package br.com.imobmatch.api.models.enums;

import lombok.Getter;

@Getter
public enum PropertyManager {

    OWNER("owner"),
    BROKER("broker");
    private String value;
    PropertyManager(String value) {}
}
