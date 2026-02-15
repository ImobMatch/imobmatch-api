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

    private BigDecimal minLandArea;
    private BigDecimal maxLandArea;

    private BigDecimal minUsableArea;
    private BigDecimal maxUsableArea;

    private BigDecimal minTotalArea;
    private BigDecimal maxTotalArea;

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

    @CNPJ(message = "CNPJ invalid")
    private String condominiumCnpj;

    private Boolean condoHasGym;
    private Boolean condoHasPool;
    private Boolean condoHasSauna;
    private Boolean condoHasSpa;
    private Boolean condoHasPartyRoom;
    private Boolean condoHasSportsCourts;
    private Boolean condoHasPlayground;
    private Boolean condoHasCoworkingSpace;
    private Boolean condoHasCinema;
    private Boolean condoHasGameRoom;
    private Boolean condoHasSharedTerrace;
    private Boolean condoHasMiniMarket;
    private Boolean condoHasPetArea;
    private Boolean condoHasBikeStorage;
    private Boolean condoHasRestaurant;
    private Boolean condoHas24hSecurity;
    private Boolean condoHasCameras;
    private Boolean condoHasElevators;
    private Boolean condoHasElectricCarStation;

    //Address
    private String street;
    private Integer number;
    private String complement;
    private String neighborhood;
    private String city;
    private BrazilianState state;
    private String zipCode;
    private String referencePoint;
}