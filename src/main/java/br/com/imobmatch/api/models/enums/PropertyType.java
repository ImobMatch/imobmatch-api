package br.com.imobmatch.api.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyType {

    HOUSE("house"),
    APARTMENT("apartment"),
    LAND("land"),
    LOT("lot"),
    PENTHOUSE("penthouse"),
    STUDIO_APARTMENT("studioApartment"),
    LOFT("loft"),
    TOWNHOUSE("townhouse"),
    STUDIO("studio"),
    VILLAGE("village"),
    ROOM("room"),
    SHOP("shop"),
    SALON("salon"),
    POINT("point"),
    WAREHOUSE("warehouse"),
    SLAB("slab"),
    AREA("area"),
    HOTEL("hotel"),
    INN("inn"),
    BUILDING("building"),
    RESORT("resort"),
    HALL("hall"),
    SITE("site"),
    SMALL_FARM("smallFarm"),
    FARM("farm"),
    RANCH("ranch"),
    HORSE_FARM("horseFarm");

    private final String value;
}
