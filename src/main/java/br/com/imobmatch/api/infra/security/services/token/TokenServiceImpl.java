package br.com.imobmatch.api.infra.security.services.token;

import br.com.imobmatch.api.dtos.auth.TokenDataDTO;
import br.com.imobmatch.api.exceptions.auth.CreateTokenException;
import br.com.imobmatch.api.exceptions.auth.TokenInvalidException;
import br.com.imobmatch.api.models.enums.TokenType;
import br.com.imobmatch.api.models.enums.UserRole;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

import br.com.imobmatch.api.models.user.User;


@Service
public class TokenServiceImpl implements TokenService{
    @Autowired
    @Qualifier("jwtSecret")
    private String secret;

    @Value("${api.security.access-token.expiration-minutes}")
    private String expirationInMinutes;

    @Value("${api.security.refresh-token.expiration-days}")
    private String expirationInDays;

    @Override
    public String generateAccessToken(User user) {
        return generateToken(user, getExpirationDateAccessToken(), TokenType.ACCESS);
    }
    @Override
    public String generateRefreshToken(User user) {
        return generateToken(user, getExpirationDateRefreshToken(), TokenType.REFRESH);
    }

    @Override
    public TokenDataDTO validateAccessToken(String token) throws TokenInvalidException {
        TokenDataDTO tokenData = getTokenDataDTO(token);
        if(tokenData.getTokenType() != TokenType.ACCESS){
            throw new TokenInvalidException("Token Type is not ACCESS");
        }
        return tokenData;
    }

    @Override
    public TokenDataDTO validateRefreshToken(String token) throws TokenInvalidException {
        TokenDataDTO tokenData = getTokenDataDTO(token);
        if(tokenData.getTokenType() != TokenType.REFRESH){
            throw new TokenInvalidException("Token type is not REFRESH");
        }
        return tokenData;
    }

    private String generateToken(User user, Date expirationDate, TokenType type) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            return JWT.create()
                    .withIssuer("imobmatch-api")
                    .withSubject(user.getEmail())
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim("id", user.getId().toString())
                    .withClaim("email", user.getEmail())
                    .withClaim("role", user.getRole().name())
                    .withClaim("token_type", type.name())
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new CreateTokenException();
        }
    }


    private TokenDataDTO getTokenDataDTO(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("imobmatch-api")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            UUID id = UUID.fromString(decodedJWT.getClaim("id").asString());
            String email = decodedJWT.getClaim("email").asString();
            UserRole role = UserRole.valueOf(decodedJWT.getClaim("role").asString());
            LocalDateTime expiration = decodedJWT.getExpiresAt()
                    .toInstant()
                    .atZone(ZoneOffset.of("-03:00"))
                    .toLocalDateTime();
            TokenType tokenType = TokenType.valueOf(decodedJWT.getClaim("token_type").asString());
            return new TokenDataDTO(id, email, role, expiration, tokenType);

        } catch (JWTVerificationException exception) {
            throw new TokenInvalidException();
        }
    }

    private Date getExpirationDateAccessToken() {
        int minutes = Integer.parseInt(this.expirationInMinutes);
        LocalDateTime ldt = LocalDateTime.now().plusMinutes(minutes);
        return Date.from(ldt.atZone(ZoneOffset.of("-03:00")).toInstant());
    }

    private Date getExpirationDateRefreshToken() {
        int days = Integer.parseInt(this.expirationInDays);
        LocalDateTime ldt = LocalDateTime.now().plusDays(days);
        return Date.from(ldt.atZone(ZoneOffset.of("-03:00")).toInstant());
    }

}
