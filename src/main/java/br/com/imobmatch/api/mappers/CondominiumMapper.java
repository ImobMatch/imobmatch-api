package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.condominium.CondominiumCreateDTO;
import br.com.imobmatch.api.dtos.property.condominium.CondominiumResponseDTO;
import br.com.imobmatch.api.dtos.property.condominium.CondominiumUpdateDTO;
import br.com.imobmatch.api.models.property.Condominium;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CondominiumMapper {
    CondominiumMapper INSTANCE = Mappers.getMapper(CondominiumMapper.class);

    @Mapping(target = "id", ignore = true)
    Condominium toEntity(CondominiumCreateDTO condominiumCreateDTO);
    CondominiumResponseDTO toDTO(Condominium condominium);

    @Mapping(target = "id", ignore = true)
    void updateCondominiumFromDTO(CondominiumUpdateDTO dto, @MappingTarget Condominium entity);

    default Boolean mapBoolean(Boolean value) {
        return value != null ? value : false;
    }
}
