package br.com.imobmatch.api.dtos.property;

import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.UUID;

public class PropertyResponseDTO {
    private UUID id;
    //por seguranca apenas o ID
    private UUID publisher;

    private AddressResponseDTO address;

    private PropertyCharacteristicResponseDTO characteristics;

    private Boolean isAvailable;

    private PropertyType type;

    private PropertyManager managedBy;

    private String ownerCpf;
}
