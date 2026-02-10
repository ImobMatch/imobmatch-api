package br.com.imobmatch.api.dtos.property;

import br.com.imobmatch.api.dtos.property.address.AddressUpdateDTO;
import br.com.imobmatch.api.dtos.property.characteristic.CharacteristicUpdateDTO;
import br.com.imobmatch.api.dtos.property.condominium.CondominiumUpdateDTO;
import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyType;
import jakarta.validation.Valid;

public class PropertyUpdateDTO {

    private PropertyType type;
    private Boolean isAvailable;
    private PropertyManager managedBy;
    private String ownerCpf;

    @Valid
    private AddressUpdateDTO address;

    @Valid
    private CharacteristicUpdateDTO characteristics;

    @Valid
    private CondominiumUpdateDTO condominium;
}
