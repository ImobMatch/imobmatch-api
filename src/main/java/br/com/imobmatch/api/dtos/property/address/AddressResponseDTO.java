package br.com.imobmatch.api.dtos.property.address;

import br.com.imobmatch.api.models.enums.BrazilianState;
import lombok.Data;

import java.util.UUID;

@Data
public class AddressResponseDTO {
    private UUID id;
    private String street;
    private Integer number;
    private String complement;
    private String neighborhood;
    private String city;
    private BrazilianState state;
    private String zipCode;
    private String referencePoint;
}

