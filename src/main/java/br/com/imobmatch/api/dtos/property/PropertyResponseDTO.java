package br.com.imobmatch.api.dtos.property;

import br.com.imobmatch.api.dtos.property.address.AddressResponseDTO;
import br.com.imobmatch.api.dtos.property.characteristic.CharacteristicResponseDTO;
import br.com.imobmatch.api.dtos.property.condominium.CondominiumResponseDTO;
import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyManager;
import br.com.imobmatch.api.models.enums.PropertyPurpose;
import br.com.imobmatch.api.models.enums.PropertyType;
import br.com.imobmatch.api.models.property.PropertyImage;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyResponseDTO {
    private UUID id;

    private String title;

    private UUID publisher;

    private BigDecimal salePrice;

    private BigDecimal rentPrice;

    private BigDecimal iptuValue;

    private PropertyPurpose purpose;

    private PropertyBusinessType businessType;

    private LocalDate publicationDate;

    private LocalDate updatedDate;

    private AddressResponseDTO address;

    private CharacteristicResponseDTO characteristic;

    private CondominiumResponseDTO condominium;

    private Boolean isAvailable;

    private PropertyType type;

    private PropertyManager managedBy;

    private String ownerCpf;

    private List<PropertyImage> images;

}
