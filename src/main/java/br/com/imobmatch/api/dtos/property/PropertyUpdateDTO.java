package br.com.imobmatch.api.dtos.property;

import br.com.imobmatch.api.dtos.property.address.AddressUpdateDTO;
import br.com.imobmatch.api.dtos.property.characteristic.CharacteristicUpdateDTO;
import br.com.imobmatch.api.dtos.property.condominium.CondominiumUpdateDTO;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyPurpose;
import br.com.imobmatch.api.models.enums.PropertyType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyUpdateDTO {

    private String title;
    private PropertyType type;
    private BigDecimal salePrice;
    private BigDecimal rentPrice;
    private BigDecimal iptuValue;
    private PropertyPurpose purpose;
    private PropertyBusinessType businessType;
    private Boolean isAvailable;
    private PropertyManager managedBy;
    private String ownerCpf;

    @Valid
    private AddressUpdateDTO address;

    @Valid
    private CharacteristicUpdateDTO characteristic;

    @Valid
    private CondominiumUpdateDTO condominium;
}
