package br.com.imobmatch.api.services.owner;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.owner.OwnerCreateDTO;
import br.com.imobmatch.api.dtos.owner.OwnerResponseDTO;
import br.com.imobmatch.api.dtos.owner.OwnerUpdateDTO;
import br.com.imobmatch.api.exceptions.owner.OwnerExistsException;
import br.com.imobmatch.api.repositories.OwnerGetAllByResponseDTO;

import java.time.LocalDate;
import java.util.UUID;

public interface OwnerService {
    /**
     * Creates a new  and a new user.
     * The Owner and User are unique and permanently linked to each other until their complete deletion.
     *
     * @param ownerCreateDTO Required data for create owner
     * @return Informative information's.
     */
    OwnerResponseDTO createOwner(OwnerCreateDTO ownerCreateDTO) throws OwnerExistsException;
    /**
     *Update authenticated owner information in the system.
     *
     * @param ownerUpdateDTO New value to be updated. Only valid and present (not null) data will be updated.
     * @return informative information's.
     */
    OwnerResponseDTO updateOwner(OwnerUpdateDTO ownerUpdateDTO);
    /**
     * Returns a detailed view of the authenticated owner in the system.
     * Including their CPF and email address.
     *
     * @return DTO containing id, name, cpf, email, role,
     * birthDate, phoneDdd, phoneNumber,isEmailVerified
     */
    OwnerResponseDTO getOwner();

    OwnerResponseDTO getOwnerById(UUID id);
    OwnerResponseDTO getOwnerByEmail(String email);
    OwnerGetAllByResponseDTO getAllOwnersByName(String name);
    OwnerGetAllByResponseDTO getAllOwnersByBirthDate(LocalDate birthDate);
    OwnerGetAllByResponseDTO getAllOwners();
    /**
     *Deletes the system owner and the user associated with them.
     *Requires the user's password to confirm the deletion.
     *It does not return any values.
     *
     * @param passwordUserDeleteDTO User password.
     */
    void deleteOwner(PasswordUserDeleteDTO passwordUserDeleteDTO);


}
