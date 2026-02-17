package br.com.imobmatch.api.services.property;

import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyFilterDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface PropertyService {

    PropertyResponseDTO createProperty(PropertyCreateDTO propertyCreateDTO);
    Page<PropertyResponseDTO> findAll(PropertyFilterDTO filter, Pageable pageable);
    PropertyResponseDTO findById(UUID id);
    PropertyResponseDTO updateProperty(UUID id, PropertyUpdateDTO propertyUpdateDTO);
    void deleteProperty(UUID id);
}
