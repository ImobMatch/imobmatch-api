package br.com.imobmatch.api.dtos.property.condominium;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CondominiumCreateDTO {

    @NotBlank(message = "name is required")
    private String name;

    private BigDecimal price;

    @CNPJ(message = "CNPJ invalid")
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
