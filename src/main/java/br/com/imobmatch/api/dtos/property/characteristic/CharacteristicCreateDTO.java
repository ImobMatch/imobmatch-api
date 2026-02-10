package br.com.imobmatch.api.dtos.property.characteristic;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacteristicCreateDTO {
    private String description;

    @Positive
    @NotNull
    private BigDecimal area;

    @Positive
    private BigDecimal landArea;

    @Positive
    private BigDecimal usableArea;

    @Positive
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
