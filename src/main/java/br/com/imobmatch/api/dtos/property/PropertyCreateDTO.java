package br.com.imobmatch.api.dtos.property;

import br.com.imobmatch.api.dtos.property.address.AddressCreateDTO;
import br.com.imobmatch.api.dtos.property.characteristic.CharacteristicCreateDTO;
import br.com.imobmatch.api.dtos.property.condominium.CondominiumCreateDTO;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyPurpose;
import br.com.imobmatch.api.models.enums.PropertyType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyCreateDTO {

    @NotNull(message = "Address is required")
    @Valid
    private AddressCreateDTO address;

    @NotNull
    private String title;

    @Positive
    private BigDecimal salePrice;

    @Positive
    private BigDecimal rentPrice;

    @Positive
    private BigDecimal iptuValue;

    @NotNull
    private PropertyPurpose purpose;

    @NotNull
    private PropertyBusinessType businessType;

    @Valid
    private CondominiumCreateDTO condominium;

    @NotNull(message = "Characteristic is required")
    @Valid
    private CharacteristicCreateDTO characteristic;

    @NotNull(message = "Property type is required")
    private PropertyType type;

    @Builder.Default
    private Boolean isAvailable = true;

    @NotNull
    private PropertyManager managedBy;

    private String ownerCpf;

}
