package br.com.imobmatch.api.dtos.property.address;

import br.com.imobmatch.api.models.enums.BrazilianState;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateDTO {

    private String street;
    private Integer number;
    private String complement;
    private String neighborhood;
    private String city;
    private BrazilianState state;
    @Pattern(regexp = "\\d{8}", message = "zipcode  contains exactly 8 digits.")
    private String zipCode;
    private String referencePoint;
}
