package br.com.imobmatch.api.services.owner;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDto;
import br.com.imobmatch.api.dtos.owner.OwnerGetResponseDto;
import br.com.imobmatch.api.dtos.owner.OwnerPostDto;
import br.com.imobmatch.api.dtos.owner.OwnerPatchDto;
import br.com.imobmatch.api.dtos.owner.OwnerResponseDto;
import br.com.imobmatch.api.exceptions.owner.OwnerExistsException;
import br.com.imobmatch.api.models.owner.Owner;

import java.util.UUID;

public interface OwnerService {

    OwnerResponseDto createOwner(OwnerPostDto owner) throws OwnerExistsException;
    OwnerResponseDto updateOwner(OwnerPatchDto owner );
    OwnerGetResponseDto getAuthenticatedOwner();
    void deleteOwner(PasswordUserDeleteDto passwordConfirm);
    OwnerGetResponseDto getOwnerByid(UUID id);
    Owner findEntityById(UUID id);

}
