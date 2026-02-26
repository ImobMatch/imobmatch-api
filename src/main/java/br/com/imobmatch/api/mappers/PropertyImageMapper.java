package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.PropertyImageDTO;
import br.com.imobmatch.api.models.property.PropertyImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PropertyImageMapper {

    @Mapping(source = "id", target = "imageID")
    PropertyImageDTO toDTO(PropertyImage entity);
    @Mapping(source = "imageID", target = "id")
    PropertyImage toEntity(PropertyImageDTO dto);

    List<PropertyImageDTO> toDTOList(List<PropertyImage> entities);

    List<PropertyImage> toEntityList(List<PropertyImageDTO> dtos);
}
