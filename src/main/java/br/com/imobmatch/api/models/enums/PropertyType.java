package br.com.imobmatch.api.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyType {

    APARTMENT("apartment"),
    HOUSE("house"),
    COMMERCIAL("commercial"),
    LOFT("loft"),
    PENTHOUSE("penthouse"),
    STUDIO("studio");

    private final String value;
}
