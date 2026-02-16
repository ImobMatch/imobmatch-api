package br.com.imobmatch.api.dtos.broker;

import br.com.imobmatch.api.models.enums.PropertyBusinessType;
import br.com.imobmatch.api.models.enums.PropertyPurpose;
import br.com.imobmatch.api.models.enums.PropertyType;
import br.com.imobmatch.api.models.property.Property;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrokerPreferencesDTO {

    private Double avgBedrooms;
    private Double avgSuites;
    private Double avgBathrooms;
    private Double avgGarage;
    private PropertyType favoriteType;
    private PropertyBusinessType businessType;
    private BigDecimal avgPrice;
    private PropertyPurpose propertyPurpose;

}
