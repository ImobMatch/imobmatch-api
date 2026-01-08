package br.com.imobmatch.api.services.auth;

import br.com.imobmatch.api.dtos.auth.AuthenticationDTO;
import br.com.imobmatch.api.dtos.auth.LoginResponseDTO;
import br.com.imobmatch.api.dtos.auth.TokenDataDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.infra.security.TokenService;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.services.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private UserService userService;

    public LoginResponseDTO login(AuthenticationDTO data) {
        var emailPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(emailPassword);
        var token =  this.tokenService.generateToken((User) auth.getPrincipal());
        return  new LoginResponseDTO(token);
    }

    public UserResponseDTO getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userService.getByEmail(email);
    }

    private TokenDataDTO getToken(String token) {
        TokenDataDTO tokenData = this.tokenService.validateToken(token);
        return tokenData;
    }

}
