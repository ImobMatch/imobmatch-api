package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.CondominiumCreateDTO;
import br.com.imobmatch.api.dtos.property.CondominiumResponseDTO;
import br.com.imobmatch.api.models.property.Condominium;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CondominiumMapper {
    CondominiumMapper INSTANCE = Mappers.getMapper(CondominiumMapper.class);

    Condominium toEntity(CondominiumCreateDTO condominiumCreateDTO);
    CondominiumResponseDTO toDTO(Condominium condominium);

    default Boolean mapBoolean(Boolean value) {
        return value != null ? value : false;
    }
}
