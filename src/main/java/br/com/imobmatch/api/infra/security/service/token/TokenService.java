package br.com.imobmatch.api.infra.security.service.token;

import br.com.imobmatch.api.dtos.auth.TokenDataDTO;
import br.com.imobmatch.api.exceptions.auth.CreateTokenException;
import br.com.imobmatch.api.exceptions.auth.TokenExpiredException;
import br.com.imobmatch.api.exceptions.auth.TokenInvalidException;
import br.com.imobmatch.api.models.user.User;

public interface TokenService {
    String generateToken(User user) throws CreateTokenException;
    TokenDataDTO validateToken(String token) throws TokenInvalidException;
}
