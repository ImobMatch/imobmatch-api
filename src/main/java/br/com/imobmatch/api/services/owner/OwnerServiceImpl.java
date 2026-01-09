package br.com.imobmatch.api.services.owner;

import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDto;
import br.com.imobmatch.api.dtos.owner.OwnerGetResponseDto;
import br.com.imobmatch.api.dtos.owner.OwnerPostDto;
import br.com.imobmatch.api.dtos.owner.OwnerPatchDto;
import br.com.imobmatch.api.dtos.owner.OwnerResponseDto;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.owner.NoValidDataProvideException;
import br.com.imobmatch.api.exceptions.owner.OwnerExistsException;
import br.com.imobmatch.api.exceptions.owner.OwnerNotExistsException;
import br.com.imobmatch.api.models.owner.Owner;
import br.com.imobmatch.api.models.phone.Phone;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.user.UserRole;
import br.com.imobmatch.api.repositories.OwnerRepository;
import br.com.imobmatch.api.services.auth.AuthService;
import br.com.imobmatch.api.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
     * @param postDto required data for create owner
     * @return informative information's. contains name and id of owner
     */
    @Override
    public OwnerResponseDto createOwner(OwnerPostDto postDto) {

        if(ownerRepository.existsOwnerByCpf(postDto.getCpf())){throw new OwnerExistsException();}
        UserResponseDTO userDto = userService.create(
                postDto.getEmail(),
                postDto.getPassword(),
                UserRole.OWNER
        );

        User user = getObjectUser(userDto.getId());
        Owner owner = new Owner();
        owner.setCpf(postDto.getCpf());
        owner.setName(postDto.getName());
        owner.setUser(user);

        ownerRepository.save(owner);
        return new OwnerResponseDto(owner.getId(), owner.getName());

    }

    /**
     *Update authenticated owner information in the system.
     *The editable information is name and CPF
     *
     * @param ownerDto New value to be updated. Only valid and present (not null) data will be updated.
     * @return informative information's. contains name and id of owner
     */
    @Override
    public OwnerResponseDto updateOwner(OwnerPatchDto ownerDto) {

        if(ownerDto.getCpf() == null && ownerDto.getName() == null){

            throw new NoValidDataProvideException();
        }

        Owner owner = getObjectOwner(authService.getMe().getId());

        if(!(ownerDto.getName() == null) && !ownerDto.getName().isBlank()){

            owner.setName(ownerDto.getName());
        }
        if(!(ownerDto.getCpf() == null) && !ownerDto.getCpf().isBlank()){

            owner.setCpf(ownerDto.getCpf());
        }

        ownerRepository.save(owner);
        return new OwnerResponseDto(owner.getId(), owner.getName());
    }

    /**
     * Returns a detailed view of the authenticated owner in the system.
     * including their CPF and email address.
     *
     * @return DTO containing id, name, cpf, email, role and primary phone of the owner
     */
    @Override
    public OwnerGetResponseDto getAuthenticatedOwner() {

        UUID userId = authService.getMe().getId();
        Owner owner = getObjectOwner(userId);
        User user = getObjectUser(userId);

        return new OwnerGetResponseDto(
                owner.getId(),
                owner.getName(),
                owner.getCpf(),
                user.getEmail(),
                user.getRole(),
                getUserPrimaryPhone(user)

        );
    }

    /**
     *Deletes the system owner and the user associated with them.
     *Requires the user's password to confirm the deletion.
     *It does not return any values.
     *
     * @param passwordUserDeleteDto User password
     */
    @Override
    public void deleteOwner(PasswordUserDeleteDto passwordUserDeleteDto) {

        //como vou confirmar a senha se não tenho acesso formal ao autenticador?
    }

    /**
     * Search for an owner in the repository and return a getDTO containing detailed information.
     *
     * @param id the owner id
     * @return DTO containing id, name, cpf, email, role and primary phone of the owner or empty string
     */
    @Override
    public OwnerGetResponseDto getOwnerByid(UUID id) {
        Owner owner = ownerRepository.findById(id).orElseThrow(OwnerNotExistsException::new);

        return new OwnerGetResponseDto(
                owner.getId(),
                owner.getName(),
                owner.getCpf(),
                owner.getUser().getEmail(),
                owner.getUser().getRole(),
                getUserPrimaryPhone(owner.getUser())
        );

    }

    /**
     * Search for the desired owner and return an instance of the owner entity.
     *
     * @param id the owner id.
     * @return one instance of owner
     */
    @Override
    public Owner findEntityById(UUID id) {
        return ownerRepository.findById(id).orElseThrow(OwnerNotExistsException::new);
    }

    private String getUserPrimaryPhone(User user){

        for(Phone phone : user.getPhones()){

            if(phone.isPrimary()){return phone.getNumber();}
        }
        return "";


    }

    private Owner getObjectOwner(UUID userId) throws OwnerNotExistsException{

        User user = userService.findEntityById(userId);
        return ownerRepository.findByUser(user)
                .orElseThrow(OwnerNotExistsException :: new);


    }

    private User getObjectUser(UUID userId){


        return userService.findEntityById(userId);
    }

}
