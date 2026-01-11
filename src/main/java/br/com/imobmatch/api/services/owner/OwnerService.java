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
    /**
     * Creates a new  and a new user.
     * The Owner and User are unique and permanently linked to each other until their complete deletion.
     *
     * @param ownerPostDTO Required data for create owner
     * @return Informative information's. contains name and id of owner
     */
    OwnerResponseDTO createOwner(OwnerPostDTO ownerPostDTO) throws OwnerExistsException;
    /**
     *Update authenticated owner information in the system.
     *The editable information is name and CPF.
     *
     * @param ownerPatchDTO New value to be updated. Only valid and present (not null) data will be updated.
     * @return informative information's. contains name and id of owner.
     */
    OwnerResponseDTO updateOwner(OwnerPatchDTO ownerPatchDTO );
    /**
     * Returns a detailed view of the authenticated owner in the system.
     * Including their CPF and email address.
     *
     * @return DTO containing id, name, cpf, email, role and primary phone of the owner
     */
    OwnerGetResponseDto getAuthenticatedOwner();
    /**
     *Deletes the system owner and the user associated with them.
     *Requires the user's password to confirm the deletion.
     *It does not return any values.
     *
     * @param passwordUserDeleteDTO User password.
     */
    void deleteOwner(PasswordUserDeleteDTO passwordUserDeleteDTO);

    /**
     * Retrieves a DTO with more detailed information about the owner and their user.
     *
     * @param id userId.
     * @return ADTO containing owner data.
     */
    OwnerGetResponseDto getOwnerByid(UUID id);

    /**
     * Get the owner entity and returns.
     *
     * @param id userId.
     * @return The instance of owner.
     */
    Owner findEntityById(UUID id);

}
