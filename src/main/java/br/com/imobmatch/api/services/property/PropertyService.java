package br.com.imobmatch.api.services.property;

import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyFilterDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.models.property.Property;
import org.springframework.stereotype.Service;

public interface PropertyService {

    PropertyResponseDTO createProperty(PropertyCreateDTO propertyCreateDTO);
    PropertyResponseDTO findProperty(PropertyFilterDTO propertyFilterDTO);
    PropertyResponseDTO updateProperty(PropertyFilterDTO propertyFilterDTO);
    PropertyResponseDTO deleteProperty(PropertyFilterDTO propertyFilterDTO);
}
