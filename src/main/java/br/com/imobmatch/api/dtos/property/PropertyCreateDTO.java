package br.com.imobmatch.api.dtos.property;

import br.com.imobmatch.api.dtos.property.address.AddressCreateDTO;
import br.com.imobmatch.api.dtos.property.characteristic.CharacteristicCreateDTO;
import br.com.imobmatch.api.dtos.property.condominium.CondominiumCreateDTO;
import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String title;

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
