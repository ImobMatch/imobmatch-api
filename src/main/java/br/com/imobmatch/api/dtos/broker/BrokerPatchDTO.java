package br.com.imobmatch.api.dtos.broker;

import br.com.imobmatch.api.models.broker.BrokerBusinessType;
import br.com.imobmatch.api.models.broker.BrokerPropertyType;
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
    
}
