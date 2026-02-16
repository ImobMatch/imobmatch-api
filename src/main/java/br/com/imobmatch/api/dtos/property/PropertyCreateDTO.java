package br.com.imobmatch.api.dtos.property;

import java.math.BigDecimal;

import br.com.imobmatch.api.dtos.property.address.AddressCreateDTO;
import br.com.imobmatch.api.dtos.property.characteristic.CharacteristicCreateDTO;
import br.com.imobmatch.api.dtos.property.condominium.CondominiumCreateDTO;
import br.com.imobmatch.api.models.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyCreateDTO {

    @NotNull(message = "Address is required")
    @Valid
    private AddressCreateDTO address;

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

    @NotNull(message = "Business type is required (SALE, RENTAL, LEASE)")
    private BrokerBusinessType businessType;

    @Positive(message = "Sale value must be positive")
    private BigDecimal saleValue;

    @Positive(message = "Rental value must be positive")
    private BigDecimal rentalValue;

}
