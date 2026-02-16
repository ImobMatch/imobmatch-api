package br.com.imobmatch.api.dtos.broker;

import java.time.LocalDate;
import java.util.Set;

import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyType;
import jakarta.validation.constraints.Past;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrokerPatchDTO {
    
    private String name;
    
    private Set<String> regionsInterest;
    
    private Set<PropertyType> propertyTypes;
    
    private Set<PropertyBusinessType> businessTypes;

    @Past
    private LocalDate birthDate;

    private String whatsAppPhoneNumber;

    private String personalPhoneNumber;
    
}
