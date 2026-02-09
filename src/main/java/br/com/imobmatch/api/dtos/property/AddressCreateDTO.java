package br.com.imobmatch.api.dtos.property;

import br.com.imobmatch.api.models.enums.BrazilianState;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreateDTO {
    @NotBlank(message = "street is required")
    private String street;

    @NotNull(message = "Number is required")
    private Integer number;

    private String complement;

    @NotBlank(message = "neighborhood is required")
    private String neighborhood;

    @NotBlank(message = "city is required")
    private String city;

    @NotNull(message = "state is required")
    private BrazilianState state;

    @NotBlank(message = "zipcode is required")
    @Pattern(regexp = "\\d{8}", message = "zipcode  contains exactly 8 digits.")
    private String zipCode;

    private String referencePoint;
}
