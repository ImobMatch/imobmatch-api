package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.characteristic.CharacteristicCreateDTO;
import br.com.imobmatch.api.dtos.property.characteristic.CharacteristicResponseDTO;
import br.com.imobmatch.api.models.property.PropertyCharacteristic;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PropertyCharacteristicMapper {

    PropertyCharacteristicMapper INSTANCE = Mappers.getMapper(PropertyCharacteristicMapper.class);

    PropertyCharacteristic toEntity(CharacteristicCreateDTO characteristicCreateDTO);
    CharacteristicResponseDTO toDTO(PropertyCharacteristic propertyCharacteristic);

    default Short mapShort(Short value) {
        return value != null ? value : (short) 0;
    }

    default Boolean mapBoolean(Boolean value) {
        return value != null ? value : false;
    }
}
