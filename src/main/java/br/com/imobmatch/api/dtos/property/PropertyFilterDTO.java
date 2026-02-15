package br.com.imobmatch.api.dtos.property;

import br.com.imobmatch.api.models.enums.BrazilianState;
import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyFilterDTO {

    //PROPERTY
    private String title;
    private PropertyType type;
    private PropertyManager managedBy;
    private Boolean isAvailable;
    private String ownerCpf;
    private UUID publisherId;

    //CHARACTERISTICS
    private BigDecimal minArea;
    private BigDecimal maxArea;

    private Short minBedrooms;
    private Short minSuites;
    private Short minBathrooms;
    private Short minGarageSpaces;
    private Short minLivingRooms;
    private Short minKitchens;

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

    //CONDOMINIUM
    private String condominiumName;
    private BigDecimal maxCondoPrice;
}