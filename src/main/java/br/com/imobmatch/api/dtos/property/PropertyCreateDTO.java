package br.com.imobmatch.api.dtos.property;

import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyType;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyCreateDTO {
    private UUID publisher;

    @NotNull(message = "Address is required")
    @Valid
    private AddressCreateDTO address;

    @NotNull(message = "Characteristic is required")
    @Valid
    private PropertyCharacteristicCreateDTO characteristics;

    @NotNull(message = "Property type is required")
    private PropertyType type;

    @Builder.Default
    private Boolean isAvailable = true;

    @NotNull
    private PropertyManager managedBy;

    private String ownerCpf;

}
