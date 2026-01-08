package br.com.imobmatch.api.infra.security.service.token;

import br.com.imobmatch.api.dtos.auth.TokenDataDTO;
import br.com.imobmatch.api.exceptions.auth.CreateTokenException;
import br.com.imobmatch.api.exceptions.auth.TokenExpiredException;
import br.com.imobmatch.api.exceptions.auth.TokenInvalidException;
import br.com.imobmatch.api.models.user.UserRole;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import br.com.imobmatch.api.models.user.User;


@Service
public class TokenServiceImpl implements TokenService{
    @Autowired
    @Qualifier("jwtSecret")
    private String secret;

    @Value("${api.security.token.expiration}")
    private String expirationInMinutes;


    @Override
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            String token = JWT.create()
                    .withIssuer("imobmatch-api")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId().toString())
                    .withClaim("email", user.getEmail())
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new CreateTokenException("Error while creating JWT" +exception.getMessage());
        }
    }

    @Override
    public TokenDataDTO validateToken(String token) throws TokenInvalidException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("imobmatch-api")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            UUID id = UUID.fromString(decodedJWT.getClaim("id").asString());
            String email = decodedJWT.getClaim("email").asString();
            UserRole role = UserRole.valueOf(decodedJWT.getClaim("role").asString());
            Instant expiration = decodedJWT.getExpiresAt().toInstant();

            return new TokenDataDTO(id, email, role, expiration);

        } catch (JWTVerificationException exception) {
            throw new TokenInvalidException();
        }
    }


    private Instant getExpirationDate() {
        int temp = Integer.parseInt(this.expirationInMinutes);
        Instant instant = LocalDateTime.now().plusMinutes(temp).toInstant(ZoneOffset.of("-03:00"));
        return instant;
    }
}
