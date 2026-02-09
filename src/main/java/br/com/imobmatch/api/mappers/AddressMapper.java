package br.com.imobmatch.api.mappers;

import br.com.imobmatch.api.dtos.property.AddressCreateDTO;
import br.com.imobmatch.api.dtos.property.AddressResponseDTO;
import jakarta.mail.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address toEntity(AddressCreateDTO addressCreateDTO);
    AddressResponseDTO toDTO(Address address);
}
