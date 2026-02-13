package br.com.imobmatch.api.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyManager {

    OWNER("owner"),
    BROKER("broker");
    private String value;
}
