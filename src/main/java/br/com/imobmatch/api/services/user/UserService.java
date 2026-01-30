package br.com.imobmatch.api.services.user;

import br.com.imobmatch.api.dtos.email.RequestValidationEmailResponseDTO;
import br.com.imobmatch.api.dtos.email.RequestValidationEmailDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailRequestDTO;
import br.com.imobmatch.api.dtos.email.ValidateEmailResponseDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.email.RequestCodeExpiredException;
import br.com.imobmatch.api.exceptions.email.RequestNotFoundException;
import br.com.imobmatch.api.exceptions.user.UserExistsException;
import br.com.imobmatch.api.exceptions.user.UserNotFoundException;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.enums.UserRole;

import java.util.UUID;



import java.util.UUID;

public interface UserService {

    // --- Basic CRUD ---
    UserResponseDTO create(String email, String password, UserRole role) throws UserExistsException;
    UserResponseDTO getById(UUID id) throws UserNotFoundException;
    UserResponseDTO getByEmail(String email) throws UserNotFoundException;
    User findEntityById(UUID id) throws UserNotFoundException;
    UserResponseDTO deleteById(UUID id, String password);

    // --- Email verification ---
    ValidateEmailResponseDTO validateEmail(ValidateEmailRequestDTO request)
            throws RequestNotFoundException, RequestCodeExpiredException;


    RequestValidationEmailResponseDTO sendEmailVerificationCodeForEmail(
            RequestValidationEmailDTO request) throws UserNotFoundException;

    void requestPasswordReset(String email);

    void resetPassword(String email, String code, String newPassword)
            throws RequestNotFoundException, RequestCodeExpiredException;
}