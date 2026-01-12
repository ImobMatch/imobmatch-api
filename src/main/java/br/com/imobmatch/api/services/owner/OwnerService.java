package br.com.imobmatch.api.services.owner;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.owner.OwnerUpdateDTO;
import br.com.imobmatch.api.dtos.owner.OwnerCreateDTO;
import br.com.imobmatch.api.dtos.owner.OwnerResponseDTO;
import br.com.imobmatch.api.exceptions.owner.OwnerExistsException;

public interface OwnerService {
    /**
     * Creates a new  and a new user.
     * The Owner and User are unique and permanently linked to each other until their complete deletion.
     *
     * @param ownerCreateDTO Required data for create owner
     * @return Informative information's. contains name and id of owner
     */
    OwnerResponseDTO createOwner(OwnerCreateDTO ownerCreateDTO) throws OwnerExistsException;
    /**
     *Update authenticated owner information in the system.
     *The editable information is name and CPF.
     *
     * @param ownerUpdateDTO New value to be updated. Only valid and present (not null) data will be updated.
     * @return informative information's. contains name and id of owner.
     */
    OwnerResponseDTO updateOwner(OwnerUpdateDTO ownerUpdateDTO);
    /**
     * Returns a detailed view of the authenticated owner in the system.
     * Including their CPF and email address.
     *
     * @return DTO containing id, name, cpf, email, role and primary phone of the owner
     */
    OwnerResponseDTO getOwner();
    /**
     *Deletes the system owner and the user associated with them.
     *Requires the user's password to confirm the deletion.
     *It does not return any values.
     *
     * @param passwordUserDeleteDTO User password.
     */
    void deleteOwner(PasswordUserDeleteDTO passwordUserDeleteDTO);


}
