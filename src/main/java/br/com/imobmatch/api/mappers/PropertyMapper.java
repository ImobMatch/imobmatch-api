package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.PropertyCreateDTO;
import br.com.imobmatch.api.dtos.property.PropertyResponseDTO;
import br.com.imobmatch.api.dtos.property.PropertyUpdateDTO;
import br.com.imobmatch.api.models.property.Property;
import org.mapstruct.*;

@Mapper( componentModel = "spring",
        uses = {AddressMapper.class, PropertyCharacteristicMapper.class,
        CondominiumMapper.class, PropertyImageMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PropertyMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "publicationDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "isAvailable", qualifiedByName = "mapToBoolean")
    Property toEntity(PropertyCreateDTO propertyCreateDTO);

    @Mapping(source = "publisher.id", target = "publisher")
    @Mapping(target = "isAvailable", qualifiedByName = "mapToBoolean")
    PropertyResponseDTO toDTO(Property property);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "isAvailable", qualifiedByName = "mapToBoolean")
    @Mapping(target = "publicationDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)

    void updatePropertyFromDTO(PropertyUpdateDTO propertyUpdateDTO, @MappingTarget Property entity);

    @Named("mapToBoolean")
    default Boolean mapBoolean(Boolean value) {
        return value != null ? value : false;
    }
}
