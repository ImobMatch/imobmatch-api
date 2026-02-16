package br.com.imobmatch.api.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyPurpose {

    RESIDENTIAL("residencial"),
    COMMERCIAL("commercial"),
    INDUSTRIAL("industrial"),
    RURAL("rural"),
    SEASONAL("seasonal"),
    CORPORATE("corporate");

    private String value;
}
