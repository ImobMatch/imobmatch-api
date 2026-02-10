package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.AddressCreateDTO;
import br.com.imobmatch.api.dtos.property.AddressResponseDTO;
import br.com.imobmatch.api.models.property.Address;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address toEntity(AddressCreateDTO addressCreateDTO);
    AddressResponseDTO toDTO(Address address);
}
