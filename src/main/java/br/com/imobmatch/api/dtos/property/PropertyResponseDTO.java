package br.com.imobmatch.api.dtos.property;

import br.com.imobmatch.api.dtos.property.address.AddressResponseDTO;
import br.com.imobmatch.api.dtos.property.characteristic.CharacteristicResponseDTO;
import br.com.imobmatch.api.dtos.property.condominium.CondominiumResponseDTO;
import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyResponseDTO {
    private UUID id;

    private String title;

    private UUID publisher;

    private AddressResponseDTO address;

    private CharacteristicResponseDTO characteristic;

    private CondominiumResponseDTO condominium;

    private Boolean isAvailable;

    private PropertyType type;

    private PropertyManager managedBy;

    private String ownerCpf;
}
