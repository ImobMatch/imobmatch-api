package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.models.property.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper( componentModel = "spring",
        uses = {AddressMapper.class, PropertyCharacteristicMapper.class,
        CondominiumMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PropertyMapper {

    Property toEntity(PropertyCreateDTO propertyCreateDTO);

    @Mapping(source = "publisher.id", target = "publisher")
    PropertyResponseDTO toDTO(Property property);
}
