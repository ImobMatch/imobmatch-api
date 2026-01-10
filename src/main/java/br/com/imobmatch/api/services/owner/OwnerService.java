package br.com.imobmatch.api.services.owner;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.owner.OwnerGetResponseDto;
import br.com.imobmatch.api.dtos.owner.OwnerPostDTO;
import br.com.imobmatch.api.dtos.owner.OwnerPatchDTO;
import br.com.imobmatch.api.dtos.owner.OwnerResponseDTO;
import br.com.imobmatch.api.exceptions.owner.OwnerExistsException;
import br.com.imobmatch.api.models.owner.Owner;

import java.util.UUID;

public interface OwnerService {

    OwnerResponseDTO createOwner(OwnerPostDTO owner) throws OwnerExistsException;
    OwnerResponseDTO updateOwner(OwnerPatchDTO owner );
    OwnerGetResponseDto getAuthenticatedOwner();
    void deleteOwner(PasswordUserDeleteDTO passwordConfirm);
    OwnerGetResponseDto getOwnerByid(UUID id);
    Owner findEntityById(UUID id);

}
