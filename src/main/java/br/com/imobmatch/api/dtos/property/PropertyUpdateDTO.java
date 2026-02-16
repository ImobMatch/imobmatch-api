package br.com.imobmatch.api.dtos.property;

import java.math.BigDecimal;

import br.com.imobmatch.api.dtos.property.address.AddressUpdateDTO;
import br.com.imobmatch.api.dtos.property.characteristic.CharacteristicUpdateDTO;
import br.com.imobmatch.api.dtos.property.condominium.CondominiumUpdateDTO;
import br.com.imobmatch.api.models.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyUpdateDTO {

    private PropertyType type;
    private Boolean isAvailable;
    private PropertyManager managedBy;
    private String ownerCpf;

    private BrokerBusinessType businessType;

    @Positive
    private BigDecimal saleValue;
    
    @Positive
    private BigDecimal rentalValue;

    @Valid
    private AddressUpdateDTO address;

    @Valid
    private CharacteristicUpdateDTO characteristic;

    @Valid
    private CondominiumUpdateDTO condominium;
}
