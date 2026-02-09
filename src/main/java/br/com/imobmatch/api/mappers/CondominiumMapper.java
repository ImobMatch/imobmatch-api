package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.CondominiumCreateDTO;
import br.com.imobmatch.api.dtos.property.CondominiunResponseDTO;
import br.com.imobmatch.api.models.property.Condominium;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CondominiumMapper {
    CondominiumMapper INSTANCE = Mappers.getMapper(CondominiumMapper.class);

    Condominium toEntity(CondominiumCreateDTO condominiumCreateDTO);
    CondominiunResponseDTO toDTO(Condominium condominium);
}
