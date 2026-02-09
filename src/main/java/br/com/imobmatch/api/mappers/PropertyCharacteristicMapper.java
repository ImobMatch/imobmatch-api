package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.PropertyCharacteristicCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyCharacteristicResponseDTO;
import br.com.imobmatch.api.models.property.PropertyCharacteristic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PropertyCharacteristicMapper {

    PropertyCharacteristicMapper INSTANCE = Mappers.getMapper(PropertyCharacteristicMapper.class);

    PropertyCharacteristic toEntity(PropertyCharacteristicCreateDTO propertyCharacteristicCreateDTO);
    PropertyCharacteristicResponseDTO toDTO(PropertyCharacteristic propertyCharacteristic);

    default Short mapShort(Short value) {
        return value != null ? value : (short) 0;
    }

    default Boolean mapBoolean(Boolean value) {
        return value != null ? value : false;
    }
}
