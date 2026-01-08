package br.com.imobmatch.api.services.auth;

import br.com.imobmatch.api.dtos.auth.AuthenticationDTO;
import br.com.imobmatch.api.dtos.auth.LoginResponseDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;

public interface AuthService {

    public LoginResponseDTO login(AuthenticationDTO data);
    public UserResponseDTO getMe();
}
