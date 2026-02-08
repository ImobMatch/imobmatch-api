package br.com.imobmatch.api.services.auth;

import br.com.imobmatch.api.dtos.auth.AuthenticationDTO;
import br.com.imobmatch.api.dtos.auth.LoginResponseDTO;
import br.com.imobmatch.api.dtos.auth.TokenDataDTO;
import br.com.imobmatch.api.dtos.user.UserResponseDTO;
import br.com.imobmatch.api.exceptions.auth.TokenInvalidException;
import br.com.imobmatch.api.exceptions.email.EmailNotVerifiedException;
import br.com.imobmatch.api.exceptions.user.UserNotFoundException;
import br.com.imobmatch.api.infra.security.services.token.TokenService;
import br.com.imobmatch.api.models.enums.TokenType;
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

    @Override
    public LoginResponseDTO login(AuthenticationDTO data) {
        var emailPassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        var auth = this.authenticationManager.authenticate(emailPassword);
        User user = (User) auth.getPrincipal();

        if (user == null) {
            throw new UserNotFoundException();
        }

        if(!user.isEmailVerified()){
            throw new EmailNotVerifiedException();        }

        String accessToken = this.tokenService.generateAccessToken(user);
        String refreshToken = this.tokenService.generateRefreshToken(user);

        return new LoginResponseDTO(accessToken, refreshToken);
    }

    @Override
    public UserResponseDTO getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return userService.getByEmail(email);
    }

    @Override
    public LoginResponseDTO refreshToken(String refreshToken) throws TokenInvalidException {
        TokenDataDTO tokenData = tokenService.validateRefreshToken(refreshToken);

        if(tokenData.getTokenType() != TokenType.REFRESH){
            throw new TokenInvalidException("Token Send not REFRESH");
        }

        User user = userService.findEntityById(tokenData.getId());
        String newAccessToken = tokenService.generateAccessToken(user);
        return new LoginResponseDTO(newAccessToken, refreshToken);
    }




}
