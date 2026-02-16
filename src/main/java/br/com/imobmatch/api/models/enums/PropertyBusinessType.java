package br.com.imobmatch.api.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyBusinessType {

    SALE("sale"),
    RENT("rent"),
    SALE_AND_RENT("saleAndRent");

    private String value;

}
