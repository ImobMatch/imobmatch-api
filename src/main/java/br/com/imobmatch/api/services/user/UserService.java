package br.com.imobmatch.api.services.user;

import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.user.UserExistsException;
import br.com.imobmatch.api.exceptions.user.UserNotFoundException;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.user.UserRole;
import java.util.UUID;

public interface UserService {

    UserResponseDTO create(String email, String password, UserRole role) throws UserExistsException;
    UserResponseDTO getById(UUID id) throws UserNotFoundException;
    UserResponseDTO getByEmail(String email) throws UserNotFoundException;
    User findEntityById(UUID id) throws UserNotFoundException;

    UserResponseDTO deleteById(UUID id, String password);
}
