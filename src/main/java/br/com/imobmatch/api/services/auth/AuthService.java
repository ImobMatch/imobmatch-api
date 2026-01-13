package br.com.imobmatch.api.services.auth;

import br.com.imobmatch.api.dtos.auth.AuthenticationDTO;
import br.com.imobmatch.api.dtos.auth.LoginResponseDTO;
import br.com.imobmatch.api.dtos.auth.PasswordUserDeleteDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.auth.TokenExpiredException;
import br.com.imobmatch.api.exceptions.auth.TokenInvalidException;

public interface AuthService {

    LoginResponseDTO login(AuthenticationDTO data);
    UserResponseDTO getMe()throws TokenExpiredException, TokenInvalidException;
    LoginResponseDTO refreshToken(String token) throws TokenExpiredException, TokenInvalidException;

}
