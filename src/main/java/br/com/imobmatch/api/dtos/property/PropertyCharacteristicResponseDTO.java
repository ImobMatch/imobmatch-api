package br.com.imobmatch.api.dtos.property;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PropertyCharacteristicResponseDTO {
    private UUID id;
    private String description;
    private BigDecimal area;
    private BigDecimal landArea;
    private BigDecimal usableArea;
    private BigDecimal totalArea;
    private Short numBedrooms;
    private Short numSuites;
    private Short numBathrooms;
    private Short numGarageSpaces;
    private Short numLivingRooms;
    private Short numKitchens;
    private Boolean hasLaundry;
    private Boolean hasCloset;
    private Boolean hasOffice;
    private Boolean hasBalcony;
    private Boolean hasTerrace;
    private Boolean hasWineCellar;
    private Boolean hasPantry;
    private Boolean hasYard;
    private Boolean hasGarden;
    private Boolean hasBarbecue;
    private Boolean hasStorage;
}
