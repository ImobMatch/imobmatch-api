package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.PropertyCharacteristicCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyCharacteristicResponseDTO;
import br.com.imobmatch.api.models.property.PropertyCharacteristic;
import org.mapstruct.factory.Mappers;

public interface PropertyCharacteristicMapper {

    PropertyCharacteristicMapper INSTANCE = Mappers.getMapper(PropertyCharacteristicMapper.class);

    PropertyCharacteristic toEntity(PropertyCharacteristicCreateDTO propertyCharacteristicCreateDTO);
    PropertyCharacteristicResponseDTO toDTO(PropertyCharacteristic propertyCharacteristic);


}
