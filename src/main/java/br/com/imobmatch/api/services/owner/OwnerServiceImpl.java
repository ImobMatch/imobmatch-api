package br.com.imobmatch.api.services.owner;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.owner.OwnerCreateDTO;
import br.com.imobmatch.api.dtos.owner.OwnerResponseDTO;
import br.com.imobmatch.api.dtos.owner.OwnerUpdateDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.auth.AuthenticationException;
import br.com.imobmatch.api.exceptions.owner.OwnerExistsException;
import br.com.imobmatch.api.exceptions.owner.OwnerNoValidDataProvideException;
import br.com.imobmatch.api.exceptions.owner.OwnerNotFoundException;
import br.com.imobmatch.api.models.owner.Owner;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.user.enums.UserRole;
import br.com.imobmatch.api.repositories.OwnerGetAllByResponseDTO;
import br.com.imobmatch.api.repositories.OwnerRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import br.com.imobmatch.api.services.user.UserService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        owner.setWhatsAppPhoneNumber(ownerCreateDTO.getWhatsAppPhoneNumber());
        owner.setPersonalPhoneNumber(ownerCreateDTO.getPersonalPhoneNumber());

        ownerRepository.save(owner);
        return buildOwnerResponseDto(owner);

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

        if (ownerUpdateDTO.getPersonalPhoneNumber()!= null && !ownerUpdateDTO.getPersonalPhoneNumber().isBlank()) {
            owner.setPersonalPhoneNumber(ownerUpdateDTO.getPersonalPhoneNumber());
            isUpdated = true;
        }

        if (ownerUpdateDTO.getWhatsAppPhoneNumber() != null && !ownerUpdateDTO.getWhatsAppPhoneNumber().isBlank()) {
            owner.setWhatsAppPhoneNumber(ownerUpdateDTO.getWhatsAppPhoneNumber());
            isUpdated = true;
        }

        if(ownerUpdateDTO.getBirthDate() != null){
            owner.setBirthDate(ownerUpdateDTO.getBirthDate());
            isUpdated = true;
        }

        if(!isUpdated){throw new OwnerNoValidDataProvideException();}

        ownerRepository.save(owner);
        return buildOwnerResponseDto(owner);
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

        return buildOwnerResponseDto(owner);
    }

    @Override
    public OwnerResponseDTO getOwnerById(UUID id) {

        Owner owner = ownerRepository.findById(id)
                .orElseThrow(OwnerNotFoundException :: new);

        return buildOwnerResponseDto(owner);
    }

    @Override
    public OwnerResponseDTO getOwnerByEmail(String email) {

        Owner owner = ownerRepository.findByUser_Email(email)
                .orElseThrow(OwnerNotFoundException :: new);

        return buildOwnerResponseDto(owner);
    }

    @Override
    public OwnerGetAllByResponseDTO getAllOwnersByName(String name) {

        return buildOwnerGetAllByResponseDTO(
                ownerRepository.findAllByName(name));

    }

    @Override
    public OwnerGetAllByResponseDTO getAllOwnersByBirthDate(LocalDate birthDate) {

        return buildOwnerGetAllByResponseDTO(
                ownerRepository.findAllByBirthDate(birthDate));
    }

    @Override
    public OwnerGetAllByResponseDTO getAllOwners() {

        return buildOwnerGetAllByResponseDTO(ownerRepository.findAll());
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

    private OwnerResponseDTO buildOwnerResponseDto(Owner owner){

        return OwnerResponseDTO.builder()
                .id(owner.getId())
                .name(owner.getName())
                .cpf(owner.getCpf())
                .email(owner.getUser().getEmail())
                .role(owner.getUser().getRole())
                .whatsAppPhoneNumber(owner.getWhatsAppPhoneNumber())
                .personalPhoneNumber(owner.getPersonalPhoneNumber())
                .birthDate(owner.getBirthDate())
                .isEmailVerified(owner.getUser().isEmailVerified())
                .build();
    }

    private OwnerGetAllByResponseDTO buildOwnerGetAllByResponseDTO(List<Owner> ownerlist){

        List<OwnerResponseDTO> ownerResponseDtoList = new ArrayList<>();

        for(Owner owner : ownerlist){

            ownerResponseDtoList.add(buildOwnerResponseDto(owner));
        }

        return OwnerGetAllByResponseDTO.builder()
                .owners(ownerResponseDtoList)
                .build();
    }
}
