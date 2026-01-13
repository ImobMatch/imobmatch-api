package br.com.imobmatch.api.infra.security.services.token;

import br.com.imobmatch.api.dtos.auth.TokenDataDTO;
import br.com.imobmatch.api.exceptions.auth.CreateTokenException;
import br.com.imobmatch.api.exceptions.auth.TokenInvalidException;
import br.com.imobmatch.api.models.user.User;

public interface TokenService {
    String generateAccessToken(User user) throws CreateTokenException;
    String generateRefreshToken(User user) throws CreateTokenException;
    TokenDataDTO validateRefreshToken(String token) throws TokenInvalidException;
    TokenDataDTO validateAccessToken(String token) throws TokenInvalidException;
}
