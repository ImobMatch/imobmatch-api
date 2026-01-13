package br.com.imobmatch.api.services.owner;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.owner.OwnerUpdateDTO;
import br.com.imobmatch.api.dtos.owner.OwnerCreateDTO;
import br.com.imobmatch.api.dtos.owner.OwnerResponseDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.auth.AuthenticationException;
import br.com.imobmatch.api.exceptions.owner.NoValidDataProvideException;
import br.com.imobmatch.api.exceptions.owner.OwnerExistsException;
import br.com.imobmatch.api.exceptions.owner.OwnerNotFoundException;
import br.com.imobmatch.api.models.owner.Owner;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.user.UserRole;
import br.com.imobmatch.api.repositories.OwnerRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import br.com.imobmatch.api.services.user.UserService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private OwnerRepository ownerRepository;
    private UserService userService;
    private AuthService authService;

    /**
     * Creates a new owner and a new user.
     * The Owner and User are unique and permanently linked to each other until their complete deletion.
     *
     * @param ownerCreateDTO Required data for create owner
     * @return Informative information's.
     */
    @Override
    @Transactional
    public OwnerResponseDTO createOwner(OwnerCreateDTO ownerCreateDTO) {

        if(ownerRepository.existsOwnerByCpf(ownerCreateDTO.getCpf())){throw new OwnerExistsException();}
        UserResponseDTO userDto = userService.create(
                ownerCreateDTO.getEmail(),
                ownerCreateDTO.getPassword(),
                UserRole.OWNER
        );

        User user = userService.findEntityById(userDto.getId());
        Owner owner = new Owner();
        owner.setCpf(ownerCreateDTO.getCpf());
        owner.setName(ownerCreateDTO.getName());
        owner.setUser(user);
        owner.setBirthDate(ownerCreateDTO.getBirthDate());
        owner.setPhoneNumber(ownerCreateDTO.getPhoneNumber());
        owner.setPhoneDdd(ownerCreateDTO.getPhoneDdd());

        ownerRepository.save(owner);
        return new OwnerResponseDTO(
            owner.getId(),
            owner.getName(),
            owner.getCpf(),
            user.getEmail(),
            user.getRole(),
            owner.getPhoneNumber(),
            owner.getPhoneDdd(),
            owner.getBirthDate(),
            user.isEmailVerified()
        );

    }

    /**
     *Update authenticated owner information in the system.
     *
     * @param ownerUpdateDTO New value to be updated. Only valid and present (not null) data will be updated.
     * @return informative information's.
     */
    @Override
    public OwnerResponseDTO updateOwner(OwnerUpdateDTO ownerUpdateDTO) {

        Owner owner = ownerRepository.findById(authService.getMe().getId())
                .orElseThrow(OwnerNotFoundException::new);

        boolean isUpdated = false;

        if (ownerUpdateDTO.getName() != null && !ownerUpdateDTO.getName().isBlank()) {
            owner.setName(ownerUpdateDTO.getName());
            isUpdated = true;
        }

        if (ownerUpdateDTO.getPhoneDdd() != null && !ownerUpdateDTO.getPhoneDdd().isBlank()) {
            owner.setPhoneDdd(ownerUpdateDTO.getPhoneDdd());
            isUpdated = true;
        }

        if (ownerUpdateDTO.getPhoneNumber() != null && !ownerUpdateDTO.getPhoneNumber().isBlank()) {
            owner.setPhoneNumber(ownerUpdateDTO.getPhoneNumber());
            isUpdated = true;
        }

        if(ownerUpdateDTO.getBirthDate() != null){
            owner.setBirthDate(ownerUpdateDTO.getBirthDate());
            isUpdated = true;
        }

        if(!isUpdated){throw new NoValidDataProvideException();}

        ownerRepository.save(owner);
        return new OwnerResponseDTO(
            owner.getId(),
            owner.getName(),
            owner.getCpf(),
            owner.getUser().getEmail(),
            owner.getUser().getRole(),
            owner.getPhoneDdd(),
            owner.getPhoneNumber(),
            owner.getBirthDate(),
            owner.getUser().isEmailVerified()
        );
    }

    /**
     * Returns a detailed view of the authenticated owner in the system.
     * Including their CPF and email address.
     *
     * @return DTO containing id, name, cpf, email, role and primary phone of the owner
     */
    @Override
    public OwnerResponseDTO getOwner() {

        Owner owner = ownerRepository.findById(authService.getMe().getId())
                .orElseThrow(OwnerNotFoundException::new);

        return new OwnerResponseDTO(
                owner.getId(),
                owner.getName(),
                owner.getCpf(),
                owner.getUser().getEmail(),
                owner.getUser().getRole(),
                owner.getPhoneDdd(),
                owner.getPhoneNumber(),
                owner.getBirthDate(),
                owner.getUser().isEmailVerified()

        );
    }

    /**
     *Deletes the system owner and the user associated with them.
     *Requires the user's password to confirm the deletion.
     *It does not return any values.
     * @param passwordUserDeleteDTO User password.
     */
    @Override
    @Transactional
    public void deleteOwner(PasswordUserDeleteDTO passwordUserDeleteDTO)throws AuthenticationException
            , OwnerNotFoundException {

        UUID userId = authService.getMe().getId();
        ownerRepository.deleteById(userId);
        userService.deleteById(userId, passwordUserDeleteDTO.getPassword());
    }
}
