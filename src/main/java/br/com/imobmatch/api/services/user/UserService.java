package br.com.imobmatch.api.services.user;

import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.user.UserExists;
import br.com.imobmatch.api.exceptions.user.UserNotFound;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.user.UserRole;

import java.util.UUID;

public interface UserService {

    UserResponseDTO create(String email, String password, UserRole role) throws UserExists;
    UserResponseDTO getById(UUID id) throws UserNotFound;
    UserResponseDTO getByEmail(String email) throws UserNotFound;
}
