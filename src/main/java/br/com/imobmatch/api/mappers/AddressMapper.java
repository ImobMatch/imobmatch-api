package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.address.AddressCreateDTO;
import br.com.imobmatch.api.dtos.property.address.AddressResponseDTO;
import br.com.imobmatch.api.dtos.property.address.AddressUpdateDTO;
import br.com.imobmatch.api.models.property.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "id", ignore = true)
    Address toEntity(AddressCreateDTO addressCreateDTO);

    AddressResponseDTO toDTO(Address address);

    @Mapping(target = "id", ignore = true)
    void updateAddressFromDTO(AddressUpdateDTO dto, @MappingTarget Address entity);
}
