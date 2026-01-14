package br.com.imobmatch.api.dtos.broker;

import java.time.LocalDate;

import br.com.imobmatch.api.models.enums.BrokerBusinessType;
import br.com.imobmatch.api.models.enums.BrokerPropertyType;
import jakarta.validation.constraints.Past;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrokerPatchDTO {
    
    private String name;
    
    private String regionInterest;
    
    private String operationCity;
    
    private BrokerPropertyType propertyType;
    
    private BrokerBusinessType businessType;

    @Past
    private LocalDate birthDate;

    private String whatsAppPhoneNumber;

    private String personalPhoneNumber;
    
}
