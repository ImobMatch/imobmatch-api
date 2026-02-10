package br.com.imobmatch.api.dtos.property.condominium;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CondominiumResponseDTO {

    private UUID id;
    private String name;
    private BigDecimal price;
    private String cnpj;

    private Boolean hasGym;
    private Boolean hasPool;
    private Boolean hasSauna;
    private Boolean hasSpa;
    private Boolean hasPartyRoom;
    private Boolean hasSportsCourts;
    private Boolean hasPlayground;
    private Boolean hasCoworkingSpace;
    private Boolean hasCinema;
    private Boolean hasGameRoom;
    private Boolean hasSharedTerrace;
    private Boolean hasMiniMarket;
    private Boolean hasPetArea;
    private Boolean hasBikeStorage;
    private Boolean hasRestaurant;
    private Boolean has24hSecurity;
    private Boolean hasCameras;
    private Boolean hasElevators;
    private Boolean hasElectricCarStation;
}
